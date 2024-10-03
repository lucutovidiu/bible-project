import {
  filter,
  finalize,
  map,
  Observable,
  of,
  switchMap,
  take,
  tap,
} from 'rxjs';

import { BibleBookService } from '../bible-book/bible-book.service';
import { SiteStoreService } from '../../store/site-store.service';
import { SiteQueryService } from '../../store/site-query.service';
import { BibleVerse, replaceNames } from '../../model/bible-verse';
import { BibleVerseStored, toBibleVerseStored } from '../../store/site-state';
import { SettingsService } from '../settings-page-service/settings.service';

export abstract class BibleLookupService {
  protected constructor(
    protected readonly bibleBookService: BibleBookService,
    protected readonly siteStoreService: SiteStoreService,
    protected readonly siteQueryService: SiteQueryService,
    protected readonly settingsService: SettingsService,
  ) {}

  findOrRetrieveVerseByChapterNumberByBook(
    chapterNumer: number,
    bookId: number,
  ): Observable<BibleVerse[]> {
    return this.siteQueryService.bibleVerseStored$.pipe(
      take(1),
      filter((storedVerses) => !!storedVerses),
      tap(() => this.loading(true)),
      switchMap((storedVerses) => {
        const foundStoredVerse = this.findVerse(
          storedVerses,
          chapterNumer,
          bookId,
        );
        if (foundStoredVerse.length === 0) {
          console.log('calling db');
          return this.bibleBookService
            .findChapterNumberByBook(chapterNumer, bookId)
            .pipe(
              map((bibleVerses) =>
                bibleVerses.map((bibleVerse) =>
                  toBibleVerseStored(bibleVerse, chapterNumer, bookId),
                ),
              ),
              tap((bibleVerseStored: BibleVerseStored[]) =>
                this.siteStoreService.storeBibleVerses(bibleVerseStored),
              ),
            );
        }
        console.log('NOT calling db');
        return of(foundStoredVerse);
      }),
      map((bibleVerseStored: BibleVerseStored[]) =>
        bibleVerseStored.map((verse) => verse.bibleVerse),
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
      finalize(() => this.loading(false)),
    );
  }

  protected abstract loading(loading: boolean): void;

  private findVerse(
    bibleVerseStored: BibleVerseStored[],
    chapterNumer: number,
    bookId: number,
  ) {
    return (
      bibleVerseStored.filter(
        (storedVerse) =>
          storedVerse.bookId === bookId &&
          storedVerse.chapterNumber === chapterNumer,
      ) || []
    );
  }
}
