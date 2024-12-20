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

import {
  SiteStoreService,
  SiteQueryService,
  BibleVerseStored,
  toBibleVerseStored,
  BibleVerse,
  BibleBookService,
} from '@bible/shared';

export abstract class BibleLookupService {
  protected constructor(
    protected readonly bibleBookService: BibleBookService,
    protected readonly siteStoreService: SiteStoreService,
    protected readonly siteQueryService: SiteQueryService,
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
