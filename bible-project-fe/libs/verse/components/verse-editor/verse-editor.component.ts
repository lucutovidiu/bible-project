import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  HostListener,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import { NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { catchError, finalize, throwError } from 'rxjs';

import {
  BibleBookService,
  BibleToastrService,
  BookEditInfo,
  buildVerseFooterDetails,
  InfiniteLoadingComponent,
  SiteStoreService,
  TextAreaUtil,
  VerseUpdateResponse,
} from '@bible/shared';

import { VerseHighlighterComponent } from '../verse-highlighter/verse-highlighter.component';

@Component({
  selector: 'bible-verse-editor',
  standalone: true,
  templateUrl: './verse-editor.component.html',
  styleUrl: './verse-editor.component.scss',
  imports: [
    NgClass,
    VerseHighlighterComponent,
    FormsModule,
    InfiniteLoadingComponent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class VerseEditorComponent implements OnInit {
  @Input({ required: true, transform: forceIntoBookEditInfo })
  bookEditInfo!: BookEditInfo;
  @Output() textUpdate: EventEmitter<string> = new EventEmitter();

  protected rows: number = 1;
  protected textWithDiacritics = '';
  protected isLoading = false;
  private readonly textAreaUtil: TextAreaUtil = new TextAreaUtil();

  constructor(
    private bibleBookService: BibleBookService,
    private readonly bibleToastrService: BibleToastrService,
    private readonly siteStoreService: SiteStoreService,
  ) {}

  ngOnInit(): void {
    this.textWithDiacritics = this.bookEditInfo.textWithDiacritics;
    this.onResize();
  }

  @HostListener('window:resize', [])
  onResize(): void {
    this.textAreaUtil.recalculateCharsPerLine(window.innerWidth);
    this.rows = this.textAreaUtil.dynamicRowCalculator(
      this.bookEditInfo.textWithDiacritics,
    );
  }

  changeText() {
    const bookEditRequest: BookEditInfo = { ...this.bookEditInfo };
    bookEditRequest.textWithDiacritics = this.textWithDiacritics;

    this.isLoading = true;
    this.bibleBookService
      .patchBibleVerseText(bookEditRequest)
      .pipe(
        catchError((err) => {
          this.handleVerseUpdateFail();
          return throwError(() => err);
        }),
        finalize(() => (this.isLoading = false)),
      )
      .subscribe((result) =>
        this.handleUpdateResponse(result, bookEditRequest),
      );
  }

  protected hasBeenEdited(): boolean {
    return this.textWithDiacritics !== this.bookEditInfo.textWithDiacritics;
  }

  private handleUpdateResponse(
    result: VerseUpdateResponse,
    bookEditInfo: BookEditInfo,
  ) {
    const toastrBody = buildVerseFooterDetails(bookEditInfo);
    if (result.result) {
      this.bibleToastrService.info(toastrBody, 'Verset updatat', false, 2000);
      this.textUpdate.emit(this.textWithDiacritics);
      this.siteStoreService.resetPartialStoredBibleVerses(bookEditInfo);
    } else {
      this.handleVerseUpdateFail();
    }
  }

  private handleVerseUpdateFail() {
    const bookInfo = this.bookEditInfo as BookEditInfo;
    this.bibleToastrService.error(
      buildVerseFooterDetails(bookInfo),
      'Verset ne-editat',
      false,
      2000,
    );
    this.textUpdate.emit(bookInfo.textWithDiacritics);
  }
}

function forceIntoBookEditInfo(bookEditInfo: BookEditInfo | null) {
  return bookEditInfo as BookEditInfo;
}
