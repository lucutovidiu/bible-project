import {BibleChapter} from "./bible-chapter";

export interface BibleVerse {
  verseNumber: number,
  text: string,
  textWithDiacritics: string
  chapter: BibleChapter
}
