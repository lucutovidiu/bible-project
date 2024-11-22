import { SettingsPageService } from '@bible/pages';
import { BibleChapter } from '@bible/shared';

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
    SettingsPageService.FATHERS_DEFAULT_NAME,
    FathersName,
  );
  const replaceFathersName_2 = replaceName(
    replaceFathersName,
    SettingsPageService.FATHERS_DEFAULT_NAME_2,
    FathersName,
  );
  return replaceName(
    replaceFathersName_2,
    SettingsPageService.SONS_DEFAULT_NAME,
    SonsName,
  );
}

function replaceName(text: string, nameToReplace: string, withName: string) {
  return text.replaceAll(nameToReplace, withName);
}
