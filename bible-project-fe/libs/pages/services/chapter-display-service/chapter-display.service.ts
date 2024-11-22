import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import {
  BibleBookService,
  SiteQueryService,
  SiteStoreService,
} from '@bible/shared';

import { BibleLookupService } from '../bible-lookup-service/bible-lookup.service';

@Injectable({
  providedIn: 'root',
})
export class ChapterDisplayService extends BibleLookupService {
  constructor(
    protected override readonly bibleBookService: BibleBookService,
    protected override readonly siteStoreService: SiteStoreService,
    protected override readonly siteQueryService: SiteQueryService,
  ) {
    super(bibleBookService, siteStoreService, siteQueryService);
  }

  get resultDisplayPageLoading$(): Observable<boolean> {
    return this.siteQueryService.resultDisplayPageLoading$;
  }

  protected loading(loading: boolean): void {
    this.siteStoreService.setResultDisplayPageLoadingState(loading);
  }
}
