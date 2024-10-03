import { Injectable } from '@angular/core';
import { BibleBookService } from '../bible-book/bible-book.service';
import { SiteStoreService } from '../../store/site-store.service';
import { SiteQueryService } from '../../store/site-query.service';
import { map, Observable, of, switchMap, take } from 'rxjs';
import { BibleVerse, replaceNames } from '../../model/bible-verse';
import { ObservableCallService } from '../../components/utility/observable-service-call/observable-call.service';
import { SettingsService } from '../settings-page-service/settings.service';

@Injectable({
  providedIn: 'root',
})
export class SearchPageService {
  constructor(
    private readonly bibleBookService: BibleBookService,
    private readonly siteStoreService: SiteStoreService,
    private readonly siteQueryService: SiteQueryService,
    private readonly settingsService: SettingsService,
  ) {}

  get searchPageLoading$(): Observable<boolean> {
    return this.siteQueryService.searchPageLoading$;
  }

  get searchPageBibleVerseResult$(): Observable<BibleVerse[] | null> {
    return this.siteQueryService.searchPageBibleVerseResult$.pipe(
      switchMap((verses) => {
        if (!verses) return of(null);
        return this.settingsService.settings$.pipe(
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
        );
      }),
    );
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
