import {Injectable} from '@angular/core';
import {filter, Observable, switchMap, take} from "rxjs";

import {BibleBookService} from "../bible-book/bible-book.service";
import {BibleBook} from "../../model/bible-book";
import {ObservableCallService} from "../../components/utility/observable-service-call/observable-call.service";
import {SiteStoreService} from "../../store/site-store.service";
import {SiteQueryService} from "../../store/site-query.service";
import {BibleLookupService} from "../bible-lookup-service/bible-lookup.service";
import {SelectedBibleBook} from "../../store/site-state";
import {BibleVerse} from "../../model/bible-verse";

@Injectable({
  providedIn: 'root'
})
export class HomeService extends BibleLookupService {
  constructor(protected override readonly bibleBookService: BibleBookService,
              protected override readonly siteStoreService: SiteStoreService,
              protected override readonly siteQueryService: SiteQueryService,) {
    super(bibleBookService, siteStoreService, siteQueryService)
  }

  get homePageLoading$(): Observable<boolean> {
    return this.siteQueryService.homePageLoading$;
  }

  get homePageBibleBooks$(): Observable<BibleBook[] | null> {
    return this.siteQueryService.homePageBibleBooks$;
  }

  get homePageSelectedBibleBook$(): Observable<SelectedBibleBook> {
    return this.siteQueryService.homePageSelectedBibleBook$;
  }

  getMenuData() {
    ObservableCallService.cachingUpdate(
      this.homePageBibleBooks$,
      (value) => this.siteStoreService.setHomePageLoadingState(value),
      () => this.bibleBookService.getMenuData(),
      (bibleBooks: BibleBook[]) => this.siteStoreService.setHomePageBibleBooks(bibleBooks),
      () => {
      }
    )
  }

  updateSelectedBibleBook(bookName: string | null,
                          bookId: number | null,
                          chaptersCount: number | null,) {
    this.siteStoreService.updateSelectedBibleBook(
      bookName,
      bookId,
      chaptersCount ? Array.from({length: chaptersCount}, (_, i) => i + 1) : []
    )
  }

  updateSelectedBibleBookChapterNumber(chapterNumber: number) {
    this.siteStoreService.updateSelectedBibleBookChapterNumber(chapterNumber)
  }

  findChapterNumberByBook(): Observable<BibleVerse[]> {
    return this.homePageSelectedBibleBook$.pipe(
      take(1),
      filter(selectedBibleBook =>
        !!selectedBibleBook.chapterNumber && !!selectedBibleBook.bookId),
      switchMap(selectedBibleBook =>
        this.findOrRetrieveVerseByChapterNumberByBook(selectedBibleBook.chapterNumber || 1, selectedBibleBook.bookId || 1)),
    )
  }

  protected loading(loading: boolean): void {
    this.siteStoreService.setHomePageLoadingState(loading)
  }
}
