import {BibleBook} from "../model/bible-book";
import {BibleVerse} from "../model/bible-verse";

export interface BibleVerseStored {
  chapterNumber: number,
  bookId: number,
  bibleVerse: BibleVerse
}

export interface SelectedBibleBook {
  bookName: string | null,
  bookId: number | null,
  chapterNumbers: number[] | null,
  chapterNumber: number | null,
  verseNumber: number | null,
}

export function toBibleVerseStored(bibleVerse: BibleVerse, chapterNumber: number, bookId: number) {
  return {
    chapterNumber,
    bookId,
    bibleVerse,
  }
}

export interface SiteState {
  homePage: {
    loading: boolean,
    updating: boolean,
    bibleBooks: BibleBook[] | null,
    selectedBibleBook: SelectedBibleBook
  },
  searchPage: {
    loading: boolean,
    updating: boolean,
    bibleVerseResult: BibleVerse[] | null,
  },
  resultDisplayPage: {
    loading: boolean,
    updating: boolean,
  },
  navbar: {
    searchText: string
  }
  bibleVerseStored: BibleVerseStored[]
}

export function createInitialState(): SiteState {
  return {
    homePage: {
      loading: false,
      updating: false,
      bibleBooks: null,
      selectedBibleBook: {
        bookName: null,
        bookId: null,
        chapterNumbers: null,
        chapterNumber: null,
        verseNumber: null,
      }
    },
    searchPage: {
      loading: false,
      updating: false,
      bibleVerseResult: null
    },
    resultDisplayPage: {
      loading: false,
      updating: false,
    },
    navbar: {
      searchText: ""
    },
    bibleVerseStored: []
  }
}