import { Injectable } from '@angular/core';
import { map, Observable, switchMap, take } from 'rxjs';

import { SettingsPageService } from '@bible/pages';

import {
  BibleBook,
  BibleVerse,
  BookEditInfo,
  replaceNames,
  VerseUpdateResponse,
} from '../../model';
import { BibleBookApiService } from '../bible-book-api/bible-book-api.service';

@Injectable({
  providedIn: 'root',
})
export class BibleBookService {
  constructor(
    private readonly bibleBookApiService: BibleBookApiService,
    protected readonly settingsService: SettingsPageService,
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
