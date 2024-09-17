import { SelectedBibleBook } from '../site-state';

export interface SiteReferencesState {
  homePage: {
    selectedBibleBook: SelectedBibleBook;
  };
  navbar: {
    searchText: string;
  };
}

export function createInitialState(): SiteReferencesState {
  return {
    homePage: {
      selectedBibleBook: {
        bookName: null,
        bookId: null,
        chapterNumbers: [],
        chapterNumber: null,
        verseNumber: null,
      },
    },
    navbar: {
      searchText: '',
    },
  };
}
