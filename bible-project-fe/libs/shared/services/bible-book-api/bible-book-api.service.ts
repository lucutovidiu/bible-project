import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ENV_TOKEN, Environment } from '@bible/env-management';

import {
  BookEditInfo,
  VerseUpdateResponse,
  BibleBook,
  BibleVerse,
} from '../../model/';

@Injectable({
  providedIn: 'root',
})
export class BibleBookApiService {
  constructor(
    private readonly http: HttpClient,
    @Inject(ENV_TOKEN) private readonly environment: Environment,
  ) {}

  getMenuData(): Observable<BibleBook[]> {
    return this.http.get<BibleBook[]>(
      `${this.environment.security.bibleProjectBE.endpoint}/menu`,
    );
  }

  findChapterNumberByBook(
    chapterNumer: number,
    bookId: number,
  ): Observable<BibleVerse[]> {
    return this.http.get<BibleVerse[]>(
      `${this.environment.security.bibleProjectBE.endpoint}/bible-finder/chapter-number/${chapterNumer}/book-id/${bookId}`,
    );
  }

  findPlacesInTheBibleByVerseText(verseText: string): Observable<BibleVerse[]> {
    return this.http.get<BibleVerse[]>(
      `${this.environment.security.bibleProjectBE.endpoint}/bible-finder/text/${verseText}`,
    );
  }

  patchBibleVerseText(
    bibleVerseChangeRequest: BookEditInfo,
  ): Observable<VerseUpdateResponse> {
    return this.http.patch<VerseUpdateResponse>(
      `${this.environment.security.bibleProjectBE.endpoint}/bible/verse`,
      bibleVerseChangeRequest,
    );
  }
}
