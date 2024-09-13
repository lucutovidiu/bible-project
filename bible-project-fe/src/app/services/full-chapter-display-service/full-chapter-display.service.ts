import { Injectable } from '@angular/core';
import {BibleBookService} from "../bible-book/bible-book.service";
import {SiteStoreService} from "../../store/site-store.service";
import {SiteQueryService} from "../../store/site-query.service";
import {BibleLookupService} from "../bible-lookup-service/bible-lookup.service";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FullChapterDisplayService extends BibleLookupService {

  constructor(protected override readonly bibleBookService: BibleBookService,
              protected override readonly siteStoreService: SiteStoreService,
              protected override readonly siteQueryService: SiteQueryService,) {
    super(bibleBookService, siteStoreService, siteQueryService)
  }

  get resultDisplayPageLoading$(): Observable<boolean> {
    return this.siteQueryService.resultDisplayPageLoading$;
  }

  protected loading(loading: boolean): void {
    this.siteStoreService.setResultDisplayPageLoadingState(loading)
  }
}
