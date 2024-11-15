import { BibleChapter } from './bible-chapter';
import { SettingsService } from '../services/settings-page-service/settings.service';

export interface BibleVerse {
  verseNumber: number;
  text: string;
  textWithDiacritics: string;
  chapter: BibleChapter | null;
}

export function replaceNames(
  bibleText: string,
  FathersName: string,
  SonsName: string,
): string {
  const replaceFathersName = replaceName(
    bibleText,
    SettingsService.FATHERS_DEFAULT_NAME,
    FathersName,
  );
  const replaceFathersName_2 = replaceName(
    replaceFathersName,
    SettingsService.FATHERS_DEFAULT_NAME_2,
    FathersName,
  );
  return replaceName(
    replaceFathersName_2,
    SettingsService.SONS_DEFAULT_NAME,
    SonsName,
  );
}

function replaceName(text: string, nameToReplace: string, withName: string) {
  return text.replaceAll(nameToReplace, withName);
}
