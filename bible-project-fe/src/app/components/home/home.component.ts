import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {CommonModule} from "@angular/common";
import {BehaviorSubject} from "rxjs";

import {BibleBookService} from "../../services/bible-book/bible-book.service";
import {BibleBook, createSelectedBook, SelectedBibleBook} from "../../services/dto/bible-book";
import {BibleVerse} from "../../services/dto/bible-verse";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'bible-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  imports: [
    CommonModule
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HomeComponent implements OnInit {
  protected readonly menuData$ = this.bibleBookService.getMenuData();
  protected chapterNumbers: number[] | null = null;
  protected selectedBook = createSelectedBook();
  protected readonly bibleVerse$ = new BehaviorSubject<BibleVerse[] | null>(null);

  constructor(private readonly bibleBookService: BibleBookService,
              private readonly activatedRoute: ActivatedRoute,
              private router: Router,) {
  }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(params => {
      if (params['bookName'] && params['bookId'] && params['chapterNumber'] && params['verseNumber']) {
        this.selectedBook = {...params as SelectedBibleBook}
        this.getRequestedBook();
      }
      if (params['bookName'] && params['bookId'] && params['chapterNumbers']) {
        this.selectedBook = createSelectedBook();
        this.selectedBook.bookName = params['bookName'];
        this.selectedBook.bookId = params['bookId'];
        this.chapterNumbers = Array.from({length: params['chapterNumbers']}, (_, i) => i + 1);
      }
    });
  }

  selectBook(bibleBookInfo: BibleBook) {
    if (!!this.selectedBook.chapterNumber && !!this.selectedBook.verseNumber) {
      this.router.navigate([''], {
        queryParams: {
          bookName: bibleBookInfo.name,
          bookId: bibleBookInfo.bookId,
          chapterNumbers: bibleBookInfo.chaptersCount
        }
      })
      return;
    }
    this.selectedBook = createSelectedBook();
    this.selectedBook.bookName = bibleBookInfo.name;
    this.selectedBook.bookId = bibleBookInfo.bookId;
    this.bibleVerse$.next(null);
    this.chapterNumbers = Array.from({length: bibleBookInfo.chaptersCount}, (_, i) => i + 1);
  }

  loadChapter(chapterNumber: number) {
    this.selectedBook.chapterNumber = chapterNumber;
    this.findChapterNumberByBook();
  }

  jumpToSelectedSection(): void {
    setTimeout(() => {
      const targetElement = document.getElementById("jump_section");
      if (targetElement) {
        // Get the position of the target element
        const rect = targetElement.getBoundingClientRect();
        // Scroll with an offset (e.g., 100px from the top)
        window.scrollTo({
          top: rect.top + window.scrollY - 200, // Adjust the offset value as needed
          behavior: 'smooth'
        });
      }
    }, 300)
  }

  private getRequestedBook() {
    this.findChapterNumberByBook()
  }

  private findChapterNumberByBook() {
    if (this.selectedBook.chapterNumber && this.selectedBook.bookId) {
      this.bibleBookService.findChapterNumberByBook(this.selectedBook.chapterNumber, this.selectedBook.bookId)
        .subscribe(bibleVerses => {
          this.bibleVerse$.next(bibleVerses);
          this.jumpToSelectedSection();
        })
    }
  }
}


