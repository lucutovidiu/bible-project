import { BibleVerse } from '../model';

export interface BibleVerseStored {
  chapterNumber: number;
  bookId: number;
  bibleVerse: BibleVerse;
}

export interface SelectedBibleBook {
  bookName: string | null;
  bookId: number | null;
  chapterNumbers: number[];
  chapterNumber: number | null;
  verseNumber: number | null;
}

export function toBibleVerseStored(
  bibleVerse: BibleVerse,
  chapterNumber: number,
  bookId: number,
) {
  return {
    chapterNumber,
    bookId,
    bibleVerse,
  };
}

export interface SiteState {
  searchPage: {
    loading: boolean;
    updating: boolean;
    bibleVerseResult: BibleVerse[] | null;
  };
  resultDisplayPage: {
    loading: boolean;
    updating: boolean;
  };
  bibleVerseStored: BibleVerseStored[];
}

export function createInitialState(): SiteState {
  return {
    searchPage: {
      loading: false,
      updating: false,
      bibleVerseResult: null,
    },
    resultDisplayPage: {
      loading: false,
      updating: false,
    },
    bibleVerseStored: [],
  };
}
