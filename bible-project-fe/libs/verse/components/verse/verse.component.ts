import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { NgClass } from '@angular/common';
import { FormsModule } from '@angular/forms';

import {
  BibleBook,
  BibleToastrService,
  BibleVerse,
  BookEditInfo,
  HtmlFunctions,
  SelectedBibleBook,
} from '@bible/shared';

import { VerseHighlighterComponent } from '../verse-highlighter/verse-highlighter.component';
import { VerseEditorComponent } from '../verse-editor/verse-editor.component';

@Component({
  selector: 'bible-verse',
  standalone: true,
  templateUrl: './verse.component.html',
  styleUrl: './verse.component.scss',
  imports: [
    NgClass,
    VerseHighlighterComponent,
    FormsModule,
    VerseEditorComponent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class VerseComponent {
  @Input({ required: true }) bibleVerse: BibleVerse | null = null;
  @Input() selectedBook: SelectedBibleBook | null = null;
  @Input() bibleBooks: BibleBook[] | null = null;
  @Input() highlightWords: string[] = [];
  @Input() highlightWholeVerse = false;
  @Input() hasJumpSection = false;
  protected shouldEdit = false;

  constructor(private readonly bibleToastrService: BibleToastrService) {}

  textUpdate(textWithDiacritics: string) {
    this.shouldEdit = !this.shouldEdit;
    this.updateBibleVerse(textWithDiacritics);
  }

  protected copyTextToClipboard() {
    const completeVerse = this.getCompleteVerse();
    completeVerse && HtmlFunctions.copyTextToClipboard(completeVerse);
  }

  protected forcedGetBookEditInfo() {
    return this.getBookEditInfo() as BookEditInfo;
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

  private updateBibleVerse(textWithDiacritics: string) {
    this.bibleVerse = {
      verseNumber: this.bibleVerse?.verseNumber,
      text: this.bibleVerse?.text,
      textWithDiacritics: textWithDiacritics,
      chapter: this.bibleVerse?.chapter,
    } as BibleVerse;
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
