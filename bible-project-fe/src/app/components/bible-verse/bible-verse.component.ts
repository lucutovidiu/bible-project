import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
} from '@angular/core';
import { BibleVerse } from '../../model/bible-verse';
import { SelectedBibleBook } from '../../store/site-state';
import { BibleBook } from '../../model/bible-book';
import { HtmlFunctions } from '../utility/html-functions';
import { BibleToastrService } from '../utility/jetty-toastr-service/bible-toastr.service';
import { NgClass } from '@angular/common';
import { BibleVerseHighlighterComponent } from '../bible-verse-highlighter/bible-verse-highlighter.component';
import { FormsModule } from '@angular/forms';
import { BibleVerseEditorComponent } from '../bible-verse-editor/bible-verse-editor.component';

@Component({
  selector: 'bible-bible-verse',
  standalone: true,
  templateUrl: './bible-verse.component.html',
  styleUrl: './bible-verse.component.scss',
  imports: [
    NgClass,
    BibleVerseHighlighterComponent,
    FormsModule,
    BibleVerseEditorComponent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BibleVerseComponent implements OnInit {
  @Input({ required: true }) bibleVerse!: BibleVerse;
  @Input() selectedBook: SelectedBibleBook | null = null;
  @Input() bibleBooks: BibleBook[] | null = null;
  @Input() highlightWords: string[] = [];
  @Input() highlightWholeVerse = false;
  @Input() hasJumpSection = false;
  protected shouldEdit = false;
  protected textWithDiacritics: string = '';

  constructor(private readonly bibleToastrService: BibleToastrService) {}

  ngOnInit(): void {
    this.textWithDiacritics = this.bibleVerse.textWithDiacritics;
  }

  textUpdate(textUpdate: string) {
    this.shouldEdit = !this.shouldEdit;
    this.textWithDiacritics = textUpdate;
  }

  protected copyTextToClipboard() {
    const completeVerse = this.getCompleteVerse();
    completeVerse && HtmlFunctions.copyTextToClipboard(completeVerse);
  }

  protected forcedGetBookEditInfo() {
    const bookEditInfo = this.getBookEditInfo() as BookEditInfo;
    bookEditInfo.textWithDiacritics = this.textWithDiacritics;
    return bookEditInfo;
  }

  protected getBookEditInfo(): BookEditInfo | null {
    const books = this.bibleBooks;
    const selectedBook = this.selectedBook;
    const verse = this.bibleVerse;
    const bookEditInfo: BookEditInfo = {
      abbreviation: null,
    } as BookEditInfo;

    if (!verse) {
      return null;
    }

    bookEditInfo.textWithDiacritics = verse.textWithDiacritics;

    if (verse.chapter) {
      bookEditInfo.abbreviation = verse.chapter.book.abbreviation;
      bookEditInfo.chapterNumber = verse.chapter.number;
      bookEditInfo.verseNumber = verse.verseNumber;
      bookEditInfo.bookName = verse.chapter.book.name;
      bookEditInfo.bookId = verse.chapter.book.bookId;
      return bookEditInfo;
    }

    if (!selectedBook) {
      return null;
    }

    if (!books) {
      bookEditInfo.chapterNumber = selectedBook.chapterNumber || 0;
      bookEditInfo.verseNumber = selectedBook.verseNumber || 0;
      bookEditInfo.bookName = selectedBook.bookName || '';
      bookEditInfo.bookId = selectedBook.bookId || 0;
      return bookEditInfo;
    }

    const bibleBook = books.find((book) => book.bookId === selectedBook.bookId);
    if (!bibleBook) return null;

    bookEditInfo.abbreviation = bibleBook.abbreviation;
    bookEditInfo.chapterNumber = selectedBook.chapterNumber || 0;
    bookEditInfo.verseNumber = verse.verseNumber;
    bookEditInfo.bookName = bibleBook.name;
    bookEditInfo.bookId = bibleBook.bookId;
    return bookEditInfo;
  }

  private displayCopyMessage(verseInfo: string) {
    this.bibleToastrService.info(verseInfo, 'Verset copiat', false, 1000);
  }

  private getCompleteVerse(): string | null {
    const bookEditInfo = this.getBookEditInfo();
    if (!bookEditInfo) {
      return null;
    }
    const verseInfo = getVerseFooterDetails(bookEditInfo);
    verseInfo.length > 0 && this.displayCopyMessage(verseInfo);

    return `${bookEditInfo.textWithDiacritics}\n${verseInfo}`;
  }
}

export function getVerseFooterDetails(bookEditInfo: BookEditInfo): string {
  if (!bookEditInfo.abbreviation)
    return `(${bookEditInfo.bookName} ${bookEditInfo.chapterNumber}:${bookEditInfo.verseNumber})`;

  return `(${bookEditInfo.abbreviation} ${bookEditInfo.chapterNumber}:${bookEditInfo.verseNumber})`;
}

export interface BookEditInfo {
  abbreviation: string | null;
  textWithDiacritics: string;
  bookName: string;
  bookId: number;
  chapterNumber: number;
  verseNumber: number;
}
