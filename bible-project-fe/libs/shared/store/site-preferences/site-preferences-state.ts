import { SettingsPageService } from '@bible/pages';

import { SelectedBibleBook } from '../site-state';
import { BibleBook } from '../../model';

export interface SettingsPageState {
  FathersName: string;
  SonsName: string;
  apiKey: string;
}

export interface SitePreferencesState {
  homePage: {
    selectedBibleBook: SelectedBibleBook;
    loading: boolean;
    updating: boolean;
    bibleBooks: BibleBook[] | null;
  };
  navbar: {
    searchText: string;
  };
  settings: SettingsPageState;
}

export function createSitePreferencesStateInitialState(): SitePreferencesState {
  return {
    homePage: {
      selectedBibleBook: {
        bookName: null,
        abbreviation: null,
        bookId: null,
        chapterNumbers: [],
        chapterNumber: null,
        verseNumber: null,
      },
      loading: false,
      updating: false,
      bibleBooks: null,
    },
    navbar: {
      searchText: '',
    },
    settings: {
      FathersName: SettingsPageService.YAUE,
      SonsName: SettingsPageService.SONS_DEFAULT_NAME,
      apiKey: '',
    },
  };
}
