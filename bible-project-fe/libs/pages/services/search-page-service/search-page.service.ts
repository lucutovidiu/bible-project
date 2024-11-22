import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import {
  BibleBookService,
  ObservableCallService,
  SiteQueryService,
  SiteStoreService,
  BibleVerse,
} from '@bible/shared';

@Injectable({
  providedIn: 'root',
})
export class SearchPageService {
  constructor(
    private readonly bibleBookService: BibleBookService,
    private readonly siteStoreService: SiteStoreService,
    private readonly siteQueryService: SiteQueryService,
  ) {}

  get searchPageLoading$(): Observable<boolean> {
    return this.siteQueryService.searchPageLoading$;
  }

  get searchPageBibleVerseResult$(): Observable<BibleVerse[] | null> {
    return this.siteQueryService.searchPageBibleVerseResult$;
  }

  findPlacesInTheBibleByVerseText(searchText: string) {
    ObservableCallService.serviceCallStatic(
      () => this.bibleBookService.findPlacesInTheBibleByVerseText(searchText),
      (loading) => this.siteStoreService.setSearchPageLoadingState(loading),
      (bibleVerseResult) =>
        this.siteStoreService.setSearchPageBibleVerseResults(bibleVerseResult),
      () => {},
    );
  }
}
