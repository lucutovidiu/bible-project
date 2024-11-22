import { Injectable } from '@angular/core';
import { filter, Observable, switchMap, take } from 'rxjs';

import {
  BibleBook,
  BibleBookService,
  BibleVerse,
  ObservableCallService,
  SelectedBibleBook,
  SitePreferencesQueryService,
  SitePreferencesStoreService,
  SiteQueryService,
  SiteStoreService,
} from '@bible/shared';

import { BibleLookupService } from '../bible-lookup-service/bible-lookup.service';

@Injectable({
  providedIn: 'root',
})
export class HomePageService extends BibleLookupService {
  constructor(
    protected override readonly bibleBookService: BibleBookService,
    protected override readonly siteStoreService: SiteStoreService,
    protected override readonly siteQueryService: SiteQueryService,
    private readonly sitePreferencesQueryService: SitePreferencesQueryService,
    private readonly sitePreferencesStoreService: SitePreferencesStoreService,
  ) {
    super(bibleBookService, siteStoreService, siteQueryService);
  }

  get homePageLoading$(): Observable<boolean> {
    return this.sitePreferencesQueryService.homePageLoading$;
  }

  get homePageBibleBooks$(): Observable<BibleBook[] | null> {
    return this.sitePreferencesQueryService.homePageBibleBooks$;
  }

  get homePageSelectedBibleBook$(): Observable<SelectedBibleBook> {
    return this.sitePreferencesQueryService.homePageSelectedBibleBook$;
  }

  getMenuData() {
    ObservableCallService.cachingUpdate(
      this.homePageBibleBooks$,
      (value) =>
        this.sitePreferencesStoreService.setHomePageLoadingState(value),
      () => this.bibleBookService.getMenuData(),
      (bibleBooks: BibleBook[]) =>
        this.sitePreferencesStoreService.setHomePageBibleBooks(bibleBooks),
      () => {},
    );
  }

  updateSelectedBibleBook(
    bookName: string | null,
    bookId: number | null,
    chaptersCount: number | null,
  ) {
    this.sitePreferencesStoreService.updateSelectedBibleBook(
      bookName,
      bookId,
      chaptersCount
        ? Array.from({ length: chaptersCount }, (_, i) => i + 1)
        : [],
    );
  }

  updateSelectedBibleBookChapterNumber(chapterNumber: number) {
    this.sitePreferencesStoreService.updateSelectedBibleBookChapterNumber(
      chapterNumber,
    );
  }

  findChapterNumberByBook(): Observable<BibleVerse[]> {
    return this.homePageSelectedBibleBook$.pipe(
      take(1),
      filter(
        (selectedBibleBook) =>
          !!selectedBibleBook.chapterNumber && !!selectedBibleBook.bookId,
      ),
      switchMap((selectedBibleBook) =>
        this.findOrRetrieveVerseByChapterNumberByBook(
          selectedBibleBook.chapterNumber || 1,
          selectedBibleBook.bookId || 1,
        ),
      ),
    );
  }

  protected loading(loading: boolean): void {
    this.sitePreferencesStoreService.setHomePageLoadingState(loading);
  }
}
