import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs";

import {BibleBook} from "../dto/bible-book";
import {BibleVerse} from "../dto/bible-verse";

@Injectable({
  providedIn: 'root'
})
export class BibleBookApiService {

  constructor(private readonly http: HttpClient) {
  }

  getMenuData(): Observable<BibleBook[]> {
    return this.http.get<BibleBook[]>("http://localhost:8989/menu");
  }

  findChapterNumberByBook(chapterNumer: number, bookId: number): Observable<BibleVerse[]> {
    return this.http.get<BibleVerse[]>(`http://localhost:8989/bible-finder/chapter-number/${chapterNumer}/book-id/${bookId}`);
  }

  findPlacesInTheBibleByVerseText(verseText: string): Observable<BibleVerse[]> {
    return this.http.get<BibleVerse[]>(`http://localhost:8989/bible-finder/text/${verseText}`);
  }
}
