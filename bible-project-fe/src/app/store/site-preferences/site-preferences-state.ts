import { SelectedBibleBook } from '../site-state';
import { SettingsService } from '../../services/settings-page-service/settings.service';
import { BibleBook } from '../../model/bible-book';

export interface SettingsPageState {
  FathersName: string;
  SonsName: string;
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
      FathersName: SettingsService.FATHERS_DEFAULT_NAME,
      SonsName: SettingsService.SONS_DEFAULT_NAME,
    },
  };
}
