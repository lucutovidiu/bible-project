import { Inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { BibleBook } from '../../model/bible-book';
import { BibleVerse } from '../../model/bible-verse';
import { ENV_TOKEN } from '../../model/environment/injection-token';
import { Environment } from '../../model/environment/environment';
import { BookEditInfo } from '../../components/bible-verse/bible-verse.component';
import { VerseUpdateResponse } from '../../model/VerseUpdateResponse';

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
