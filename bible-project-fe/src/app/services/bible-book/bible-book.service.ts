import { Injectable } from '@angular/core';

import {BibleBookApiService} from "../bible-book-api/bible-book-api.service";
import {Observable} from "rxjs";
import {BibleBook} from "../dto/bible-book";
import {BibleVerse} from "../dto/bible-verse";

@Injectable({
  providedIn: 'root'
})
export class BibleBookService {

  constructor(private readonly bibleBookApiService: BibleBookApiService) { }

  getMenuData(): Observable<BibleBook[]>{
    return this.bibleBookApiService.getMenuData();
  }

  findChapterNumberByBook(chapterNumer: number, bookId: number): Observable<BibleVerse[]>{
    return this.bibleBookApiService.findChapterNumberByBook(chapterNumer, bookId);
  }

  findPlacesInTheBibleByVerseText(verseText: string): Observable<BibleVerse[]>{
    return this.bibleBookApiService.findPlacesInTheBibleByVerseText(verseText);
  }
}
