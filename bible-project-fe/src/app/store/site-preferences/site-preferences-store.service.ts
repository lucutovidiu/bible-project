import {Store, StoreConfig} from "@datorama/akita";
import {Injectable} from "@angular/core";

import {BibleVerseStored, createInitialState, SiteReferencesState} from "./site-references-state";
import {BibleBook} from "../model/bible-book";
import {BibleVerse} from "../model/bible-verse";

@Injectable({
  providedIn: 'root',
})
@StoreConfig({
  name: 'site-store',
  resettable: true,
  cache: {ttl: 12 * 60 * 60 * 1000}, // TTL (time-to-live) to one day (i.e., 12 hours), you can specify a value of 86400000 milliseconds (24 hours x 60 minutes x 60 seconds x 1000 milliseconds)
})
export class SitePreferencesStoreService extends Store<SiteReferencesState> {

  constructor() {
    super(createInitialState());
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

  public setSearchPageLoadingState(loading: boolean) {
    this.update((state) => ({
      ...state,
      searchPage: {
        ...state.searchPage,
        loading,
      },
    }));
  }

  public setSearchPageBibleVerseResults(bibleVerseResult: BibleVerse[]) {
    this.update((state) => ({
      ...state,
      searchPage: {
        ...state.searchPage,
        bibleVerseResult,
      },
    }));
  }

  public setResultDisplayPageLoadingState(loading: boolean) {
    this.update((state) => ({
      ...state,
      resultDisplayPage: {
        ...state.resultDisplayPage,
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

  storeBibleVerses(bibleVerseStored: BibleVerseStored[]) {
    this.update((state) => ({
      ...state,
      bibleVerseStored: [
        ...state.bibleVerseStored,
        ...bibleVerseStored
      ]
    }));
  }

  updateSelectedBibleBook(bookName: string | null,
                          bookId: number | null,
                          chapterNumbers: number[]) {
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
      }
    }));
  }

  updateSelectedBibleBookChapterNumber(chapterNumber: number) {
    this.update((state) => ({
      ...state,
      homePage: {
        ...state.homePage,
        selectedBibleBook: {
          ...state.homePage.selectedBibleBook,
          chapterNumber
        },
      }
    }));
  }

  updateNavbarSearchBox(searchText: string) {
    this.update((state) => ({
      ...state,
      navbar: {
        searchText
      }
    }));
  }
}
