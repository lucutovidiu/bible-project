import { Store, StoreConfig } from '@datorama/akita';
import { Injectable } from '@angular/core';

import {
  createSitePreferencesStateInitialState,
  SitePreferencesState,
} from './site-preferences-state';
import { BibleBook } from '../../model/bible-book';

@Injectable({
  providedIn: 'root',
})
@StoreConfig({
  name: 'site-preferences-store',
  resettable: true,
  cache: { ttl: 14 * 24 * 60 * 60 * 1000 }, // TTL (time-to-live) to one day (i.e., 2 weeks), you can specify a value of 86400000 milliseconds (24 hours x 60 minutes x 60 seconds x 1000 milliseconds)
})
export class SitePreferencesStoreService extends Store<SitePreferencesState> {
  constructor() {
    super(createSitePreferencesStateInitialState());
  }

  updateSelectedBibleBook(
    bookName: string | null,
    bookId: number | null,
    chapterNumbers: number[],
  ) {
    this.update((state) => ({
      ...state,
      homePage: {
        ...state.homePage,
        selectedBibleBook: {
          ...state.homePage.selectedBibleBook,
          bookName,
          bookId,
          chapterNumbers,
          chapterNumber: null,
        },
      },
    }));
  }

  public setHomePageLoadingState(loading: boolean) {
    this.update((state) => ({
      ...state,
      homePage: {
        ...state.homePage,
        loading,
      },
    }));
  }

  setHomePageBibleBooks(bibleBooks: BibleBook[]) {
    this.update((state) => ({
      ...state,
      homePage: {
        ...state.homePage,
        bibleBooks: bibleBooks,
      },
    }));
  }

  updateSelectedBibleBookChapterNumber(chapterNumber: number) {
    this.update((state) => ({
      ...state,
      homePage: {
        ...state.homePage,
        selectedBibleBook: {
          ...state.homePage.selectedBibleBook,
          chapterNumber,
        },
      },
    }));
  }

  updateNavbarSearchBox(searchText: string) {
    this.update((state) => ({
      ...state,
      navbar: {
        searchText,
      },
    }));
  }

  updateFatherName(FathersName: string) {
    this.update((state) => ({
      ...state,
      settings: {
        ...state.settings,
        FathersName,
      },
    }));
  }

  updateSonsName(SonsName: string) {
    this.update((state) => ({
      ...state,
      settings: {
        ...state.settings,
        SonsName,
      },
    }));
  }
}
