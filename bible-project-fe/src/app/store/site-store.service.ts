import { Store, StoreConfig } from '@datorama/akita';
import { Injectable } from '@angular/core';

import { BibleVerseStored, createInitialState, SiteState } from './site-state';
import { BibleVerse } from '../model/bible-verse';

@Injectable({
  providedIn: 'root'
})
@StoreConfig({
  name: 'site-store',
  resettable: true,
  cache: { ttl: 7 * 12 * 12 * 60 * 60 * 1000 } // TTL (time-to-live) to one day (i.e., 12 hours), you can specify a value of 86400000 milliseconds (24 hours x 60 minutes x 60 seconds x 1000 milliseconds)
})
export class SiteStoreService extends Store<SiteState> {
  constructor() {
    super(createInitialState());
  }

  public setSearchPageLoadingState(loading: boolean) {
    this.update((state) => ({
      ...state,
      searchPage: {
        ...state.searchPage,
        loading
      }
    }));
  }

  public setSearchPageBibleVerseResults(bibleVerseResult: BibleVerse[]) {
    this.update((state) => ({
      ...state,
      searchPage: {
        ...state.searchPage,
        bibleVerseResult
      }
    }));
  }

  public setResultDisplayPageLoadingState(loading: boolean) {
    this.update((state) => ({
      ...state,
      resultDisplayPage: {
        ...state.resultDisplayPage,
        loading
      }
    }));
  }

  storeBibleVerses(bibleVerseStored: BibleVerseStored[]) {
    this.update((state) => ({
      ...state,
      bibleVerseStored: [...state.bibleVerseStored, ...bibleVerseStored]
    }));
  }
}
