import { Injectable } from '@angular/core';

import { BibleBookApiService } from '../bible-book-api/bible-book-api.service';
import { map, Observable, switchMap, take } from 'rxjs';
import { BibleBook } from '../../model/bible-book';
import { BibleVerse, replaceNames } from '../../model/bible-verse';
import { SettingsService } from '../settings-page-service/settings.service';
import { BookEditInfo } from '../../components/bible-verse/bible-verse.component';
import { VerseUpdateResponse } from '../../model/VerseUpdateResponse';

@Injectable({
  providedIn: 'root',
})
export class BibleBookService {
  constructor(
    private readonly bibleBookApiService: BibleBookApiService,
    protected readonly settingsService: SettingsService,
  ) {}

  getMenuData(): Observable<BibleBook[]> {
    return this.bibleBookApiService.getMenuData();
  }

  patchBibleVerseText(
    bibleVerseChangeRequest: BookEditInfo,
  ): Observable<VerseUpdateResponse> {
    return this.bibleBookApiService.patchBibleVerseText(
      bibleVerseChangeRequest,
    );
  }

  findChapterNumberByBook(
    chapterNumer: number,
    bookId: number,
  ): Observable<BibleVerse[]> {
    return this.bibleBookApiService
      .findChapterNumberByBook(chapterNumer, bookId)
      .pipe(
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

  findPlacesInTheBibleByVerseText(verseText: string): Observable<BibleVerse[]> {
    return this.bibleBookApiService
      .findPlacesInTheBibleByVerseText(verseText)
      .pipe(
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
}
