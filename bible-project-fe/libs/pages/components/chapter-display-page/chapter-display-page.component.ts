import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { AsyncPipe, JsonPipe } from '@angular/common';

import { VerseManagerComponent } from '@bible/verse';
import {
  BibleVerse,
  HtmlFunctions,
  LoadingIndicatorBoxComponent,
  SelectedBibleBook,
} from '@bible/shared';

import { ChapterDisplayService } from '../../services/chapter-display-service/chapter-display.service';

@Component({
  standalone: true,
  selector: 'bible-chapter-display-page',
  templateUrl: './chapter-display-page.component.html',
  styleUrl: './chapter-display-page.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    VerseManagerComponent,
    LoadingIndicatorBoxComponent,
    AsyncPipe,
    JsonPipe,
  ],
})
export class ChapterDisplayPageComponent implements OnInit {
  protected selectedBook: SelectedBibleBook | null = null;
  protected readonly bibleVerse$ = new BehaviorSubject<BibleVerse[] | null>(
    null,
  );
  protected readonly resultDisplayPageLoading$ =
    this.chapterDisplayService.resultDisplayPageLoading$;

  constructor(
    private readonly chapterDisplayService: ChapterDisplayService,
    private readonly activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe((params) => {
      if (
        params['bookName'] &&
        params['bookId'] &&
        params['chapterNumber'] &&
        params['verseNumber']
      ) {
        this.updateSelectedBook(params);
        this.getRequestedBook();
      }
    });
  }

  private updateSelectedBook(params: Params) {
    this.selectedBook = {
      bookName: params['bookName'],
      abbreviation: params['abbreviation'] || null,
      bookId: Number.parseInt(params['bookId']),
      chapterNumber: Number.parseInt(params['chapterNumber']),
      verseNumber: Number.parseInt(params['verseNumber']),
      chapterNumbers: [],
    };
  }

  private getRequestedBook() {
    this.findChapterNumberByBook();
  }

  private findChapterNumberByBook() {
    if (
      this.selectedBook &&
      this.selectedBook.chapterNumber &&
      this.selectedBook.bookId
    ) {
      this.chapterDisplayService
        .findOrRetrieveVerseByChapterNumberByBook(
          this.selectedBook.chapterNumber,
          this.selectedBook.bookId,
        )
        .subscribe((bibleVerses) => {
          this.bibleVerse$.next(bibleVerses);
          HtmlFunctions.jumpToSection('jump_section', 200, 300);
        });
    }
  }
}
