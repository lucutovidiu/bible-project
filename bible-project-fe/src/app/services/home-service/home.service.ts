import { Injectable } from '@angular/core';
import { filter, map, Observable, switchMap, take } from 'rxjs';

import { BibleBookService } from '../bible-book/bible-book.service';
import { BibleBook } from '../../model/bible-book';
import { ObservableCallService } from '../../components/utility/observable-service-call/observable-call.service';
import { SiteStoreService } from '../../store/site-store.service';
import { SiteQueryService } from '../../store/site-query.service';
import { BibleLookupService } from '../bible-lookup-service/bible-lookup.service';
import { SelectedBibleBook } from '../../store/site-state';
import { BibleVerse, replaceNames } from '../../model/bible-verse';
import { SitePreferencesQueryService } from '../../store/site-preferences/site-preferences-query.service';
import { SitePreferencesStoreService } from '../../store/site-preferences/site-preferences-store.service';
import { SettingsService } from '../settings-page-service/settings.service';

@Injectable({
  providedIn: 'root',
})
export class HomeService extends BibleLookupService {
  constructor(
    protected override readonly bibleBookService: BibleBookService,
    protected override readonly siteStoreService: SiteStoreService,
    protected override readonly siteQueryService: SiteQueryService,
    private readonly sitePreferencesQueryService: SitePreferencesQueryService,
    private readonly sitePreferencesStoreService: SitePreferencesStoreService,
    protected override readonly settingsService: SettingsService,
  ) {
    super(
      bibleBookService,
      siteStoreService,
      siteQueryService,
      settingsService,
    );
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
      (value) => this.sitePreferencesStoreService.setHomePageLoadingState(value),
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
      switchMap((verses) =>
        this.settingsService.settings$.pipe(
          take(1),
          map((settings) =>
            verses.map((verse) => ({
              ...verse,
              text: replaceNames(
                verse.text,
                settings.FathersName,
                settings.SonsName,
              ),
              textWithDiacritics: replaceNames(
                verse.textWithDiacritics,
                settings.FathersName,
                settings.SonsName,
              ),
            })),
          ),
        ),
      ),
    );
  }

  protected loading(loading: boolean): void {
    this.sitePreferencesStoreService.setHomePageLoadingState(loading);
  }
}
