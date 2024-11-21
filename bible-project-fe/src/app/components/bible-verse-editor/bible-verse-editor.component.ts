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
import { BibleVerseHighlighterComponent } from '../bible-verse-highlighter/bible-verse-highlighter.component';
import { FormsModule } from '@angular/forms';
import {
  BookEditInfo,
  getVerseFooterDetails,
} from '../bible-verse/bible-verse.component';
import { BibleBookService } from '../../services/bible-book/bible-book.service';
import { BibleToastrService } from '../utility/jetty-toastr-service/bible-toastr.service';
import { VerseUpdateResponse } from '../../model/VerseUpdateResponse';
import { SiteStoreService } from '../../store/site-store.service';

@Component({
  selector: 'bible-verse-editor',
  standalone: true,
  templateUrl: './bible-verse-editor.component.html',
  styleUrl: './bible-verse-editor.component.scss',
  imports: [NgClass, BibleVerseHighlighterComponent, FormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BibleVerseEditorComponent implements OnInit {
  @Input({ required: true }) bookEditInfo!: BookEditInfo;
  @Output() textUpdate: EventEmitter<string> = new EventEmitter();

  charsPerLine: number = 50;
  rows: number = 1;
  protected textWithDiacritics = '';

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
    this.updateCharsPerLine();
    this.calculateRows();
  }

  changeText() {
    const bookEditRequest: BookEditInfo = this.bookEditInfo;
    bookEditRequest.textWithDiacritics = this.textWithDiacritics;
    this.bibleBookService
      .patchBibleVerseText(bookEditRequest)
      .subscribe((result) =>
        this.handleUpdateResponse(result, this.bookEditInfo as BookEditInfo),
      );
  }

  private handleUpdateResponse(
    result: VerseUpdateResponse,
    bookEditInfo: BookEditInfo,
  ) {
    const toastrBody = getVerseFooterDetails(bookEditInfo);
    if (result.result) {
      this.bibleToastrService.info(toastrBody, 'Verset updatat', false, 2000);
      this.textUpdate.emit(this.textWithDiacritics);
      this.siteStoreService.resetStoredBibleVerses();
    } else {
      this.bibleToastrService.error(
        toastrBody,
        'Verset neupdatat',
        false,
        2000,
      );
      this.textUpdate.emit(bookEditInfo.textWithDiacritics);
    }
  }

  private calculateRows(): void {
    const text = '' + this.bookEditInfo.textWithDiacritics;
    const adjustedText = text.replace(/\n/g, ' '.repeat(this.charsPerLine)); // Each newline counts as a full line
    this.rows = Math.ceil(adjustedText.length / this.charsPerLine);
  }

  private updateCharsPerLine(): void {
    const width = window.innerWidth;

    // Adjust based on screen width (simple example, customize as needed)
    if (width >= 1024) {
      // Desktop: Larger screens
      this.charsPerLine = 105;
    } else if (width >= 768) {
      // Tablet: Medium screens
      this.charsPerLine = 60;
    } else if (width >= 500) {
      // Tablet: Medium screens
      this.charsPerLine = 50;
    } else if (width >= 440) {
      // Tablet: Medium screens
      this.charsPerLine = 40;
    } else {
      // Mobile: Smaller screens
      this.charsPerLine = 35;
    }
  }
}
