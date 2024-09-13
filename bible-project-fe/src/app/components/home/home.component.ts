import {ChangeDetectionStrategy, Component, OnDestroy, OnInit} from '@angular/core';
import {CommonModule} from "@angular/common";
import {BehaviorSubject, catchError, throwError} from "rxjs";
import {BibleBook} from "../../model/bible-book";
import {BibleVerse} from "../../model/bible-verse";
import {LoadingIndicatorBoxComponent} from "../loading-indicator-box/loading-indicator-box.component";
import {HomeService} from "../../services/home-service/home.service";
import {InfiniteLoadingComponent} from "../infinite-loading/infinite-loading.component";
import {HtmlFunctions} from "../utility/html-functions";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";
import {SelectedBibleBook} from "../../store/site-state";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {BibleToastrService} from "../utility/jetty-toastr-service/bible-toastr.service";

@Component({
  selector: 'bible-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  imports: [
    CommonModule,
    LoadingIndicatorBoxComponent,
    InfiniteLoadingComponent
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HomeComponent implements OnInit, OnDestroy {
  protected readonly bibleBooks$ = this.homeService.homePageBibleBooks$;
  protected homePageLoading$ = this.homeService.homePageLoading$;
  protected selectedBook: SelectedBibleBook | null = null;
  protected readonly bibleVerse$ = new BehaviorSubject<BibleVerse[] | null>(null);

  constructor(private readonly homeService: HomeService,
              private readonly bibleToastrService: BibleToastrService) {
    this.homeService.homePageSelectedBibleBook$.pipe(
      takeUntilDestroyed(),
    )
      .subscribe(selectedBook => {
          this.selectedBook = selectedBook;
        }
      )
  }

  selectBook(bibleBookInfo: BibleBook) {
    this.bibleVerse$.next(null);
    this.homeService.updateSelectedBibleBook(bibleBookInfo.name, bibleBookInfo.bookId, bibleBookInfo.chaptersCount)
  }

  loadChapter(chapterNumber: number) {
    this.homeService.updateSelectedBibleBookChapterNumber(chapterNumber);
    this.findChapterNumberByBook();
  }

  ngOnInit(): void {
    this.homeService.getMenuData();
    this.findChapterNumberByBook();
  }

  private findChapterNumberByBook() {
    this.homeService.findChapterNumberByBook().pipe(
      catchError(error => {
        this.bibleVerse$.next(null);
        this.bibleToastrService.error("Incearca mai tarziu la acest capitol", "Eroare de server", false, 2000)
        return throwError(() => error);
      })
    ).subscribe(bibleVerses => {
      this.bibleVerse$.next(bibleVerses);
      HtmlFunctions.jumpToSection("section_chapter", 100);
    })
  }

  ngOnDestroy(): void {
    this.homeService.updateSelectedBibleBook(null, null,null)
  }
}


