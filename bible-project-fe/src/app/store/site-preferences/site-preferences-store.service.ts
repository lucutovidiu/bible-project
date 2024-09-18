import { Store, StoreConfig } from '@datorama/akita';
import { Injectable } from '@angular/core';

import {
  createSitePreferencesStateInitialState,
  SitePreferencesState,
} from './site-preferences-state';

@Injectable({
  providedIn: 'root',
})
@StoreConfig({
  name: 'site-preferences-store',
  resettable: true,
  cache: { ttl: 12 * 60 * 60 * 1000 }, // TTL (time-to-live) to one day (i.e., 12 hours), you can specify a value of 86400000 milliseconds (24 hours x 60 minutes x 60 seconds x 1000 milliseconds)
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
}
