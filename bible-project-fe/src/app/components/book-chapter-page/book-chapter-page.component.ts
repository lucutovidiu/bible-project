import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { AsyncPipe } from '@angular/common';

import { BibleVerse } from '../../model/bible-verse';
import { HtmlFunctions } from '../utility/html-functions';
import { FullChapterDisplayService } from '../../services/full-chapter-display-service/full-chapter-display.service';
import { SelectedBibleBook } from '../../store/site-state';
import { BibleVerseComponent } from '../bible-verse/bible-verse.component';
import { LoadingIndicatorBoxComponent } from '../loading-indicator-box/loading-indicator-box.component';

@Component({
  standalone: true,
  selector: 'book-chapter-page',
  templateUrl: './book-chapter-page.component.html',
  styleUrl: './book-chapter-page.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [BibleVerseComponent, LoadingIndicatorBoxComponent, AsyncPipe],
})
export class BookChapterPageComponent implements OnInit {
  protected selectedBook: SelectedBibleBook | null = null;
  protected readonly bibleVerse$ = new BehaviorSubject<BibleVerse[] | null>(
    null,
  );
  protected readonly resultDisplayPageLoading$ =
    this.fullChapterDisplayService.resultDisplayPageLoading$;

  constructor(
    private readonly fullChapterDisplayService: FullChapterDisplayService,
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
      this.fullChapterDisplayService
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
