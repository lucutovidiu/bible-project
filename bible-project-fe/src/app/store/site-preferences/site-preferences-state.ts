import { SelectedBibleBook } from '../site-state';

export interface SitePreferencesState {
  homePage: {
    selectedBibleBook: SelectedBibleBook;
  };
  navbar: {
    searchText: string;
  };
}

export function createSitePreferencesStateInitialState(): SitePreferencesState {
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
