import {
  ChangeDetectionStrategy,
  Component,
  HostListener,
  OnInit,
} from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { BehaviorSubject, catchError, throwError } from 'rxjs';
import { CommonModule } from '@angular/common';

import {
  BibleBook,
  BibleToastrService,
  BibleVerse,
  HtmlFunctions,
  InfiniteLoadingComponent,
  LoadingIndicatorBoxComponent,
  SelectedBibleBook,
} from '@bible/shared';
import { VerseComponent } from '@bible/verse';

import { HomePageService } from '../../services/home-page-service/home-page.service';

@Component({
  selector: 'bible-home-page',
  standalone: true,
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.scss',
  imports: [
    CommonModule,
    LoadingIndicatorBoxComponent,
    InfiniteLoadingComponent,
    VerseComponent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomePageComponent implements OnInit {
  protected readonly bibleBooks$ = this.homeService.homePageBibleBooks$;
  protected homePageLoading$ = this.homeService.homePageLoading$;
  protected selectedBook: SelectedBibleBook | null = null;
  protected readonly bibleVerse$ = new BehaviorSubject<BibleVerse[] | null>(
    null,
  );

  constructor(
    private readonly homeService: HomePageService,
    private readonly bibleToastrService: BibleToastrService,
  ) {
    this.homeService.homePageSelectedBibleBook$
      .pipe(takeUntilDestroyed())
      .subscribe((selectedBook) => {
        this.selectedBook = selectedBook;
      });
  }

  selectBook(bibleBookInfo: BibleBook) {
    this.bibleVerse$.next(null);
    this.homeService.updateSelectedBibleBook(
      bibleBookInfo.name,
      bibleBookInfo.bookId,
      bibleBookInfo.chaptersCount,
    );
    HtmlFunctions.jumpToSection('section_chapter', 50, 170);
  }

  loadChapter(chapterNumber: number) {
    this.homeService.updateSelectedBibleBookChapterNumber(chapterNumber);
    this.findChapterNumberByBook();
  }

  ngOnInit(): void {
    this.homeService.getMenuData();
    this.findChapterNumberByBook();
  }

  @HostListener('window:keyup', ['$event'])
  keyEvent(event: KeyboardEvent) {
    if (event.key == KEY_CODE.RIGHT_ARROW) {
      if (this.selectedBook !== null) {
        if (
          this.selectedBook.chapterNumber !==
          this.selectedBook.chapterNumbers.length
        ) {
          if (this.selectedBook.chapterNumber) {
            this.loadChapter(this.selectedBook.chapterNumber + 1);
          }
        }
      }
    }
    if (event.key == KEY_CODE.LEFT_ARROW) {
      if (this.selectedBook !== null) {
        if (
          this.selectedBook.chapterNumber !==
          this.selectedBook.chapterNumbers.length
        ) {
          if (this.selectedBook.chapterNumber) {
            this.loadChapter(this.selectedBook.chapterNumber - 1);
          }
        }
      }
    }
  }

  private findChapterNumberByBook() {
    this.homeService
      .findChapterNumberByBook()
      .pipe(
        catchError((error) => {
          this.bibleVerse$.next(null);
          this.bibleToastrService.error(
            'Incearca mai tarziu la acest capitol',
            'Eroare de server',
            false,
            2000,
          );
          return throwError(() => error);
        }),
      )
      .subscribe((bibleVerses) => {
        this.bibleVerse$.next(bibleVerses);
        HtmlFunctions.jumpToSection('section_verses', 50, 170);
      });
  }
}

export enum KEY_CODE {
  UP_ARROW = 'ArrowUp',
  DOWN_ARROW = 'ArrowDown',
  RIGHT_ARROW = 'ArrowRight',
  LEFT_ARROW = 'ArrowLeft',
}
