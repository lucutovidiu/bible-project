import { Query } from '@datorama/akita';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { BibleVerseStored, SiteState } from './site-state';
import { SiteStoreService } from './site-store.service';
import { BibleBook } from '../model/bible-book';
import { BibleVerse } from '../model/bible-verse';

@Injectable({
  providedIn: 'root',
})
export class SiteQueryService extends Query<SiteState> {
  readonly homePageLoading$: Observable<boolean> = this.select(
    (state) => state.homePage.loading,
  );
  readonly searchPageLoading$: Observable<boolean> = this.select(
    (state) => state.searchPage.loading,
  );
  readonly resultDisplayPageLoading$: Observable<boolean> = this.select(
    (state) => state.resultDisplayPage.loading,
  );
  readonly homePageBibleBooks$: Observable<BibleBook[] | null> = this.select(
    (state) => state.homePage.bibleBooks,
  );
  readonly searchPageBibleVerseResult$: Observable<BibleVerse[] | null> =
    this.select((state) => state.searchPage.bibleVerseResult);
  readonly bibleVerseStored$: Observable<BibleVerseStored[]> =
    this.select('bibleVerseStored');

  constructor(protected override store: SiteStoreService) {
    super(store);
  }
}
