import {
  ChangeDetectionStrategy,
  Component,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { BehaviorSubject } from 'rxjs';

import { BibleVerse } from '../../model/bible-verse';
import { HtmlFunctions } from '../utility/html-functions';
import { FullChapterDisplayService } from '../../services/full-chapter-display-service/full-chapter-display.service';
import { SelectedBibleBook } from '../../store/site-state';

@Component({
  selector: 'bible-result-display-full-chapter',
  templateUrl: './result-display-full-chapter.component.html',
  styleUrl: './result-display-full-chapter.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ResultDisplayFullChapterComponent implements OnInit, OnDestroy {
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

  ngOnDestroy(): void {
    console.log('ResultDisplayFullChapterComponent destroyed');
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
