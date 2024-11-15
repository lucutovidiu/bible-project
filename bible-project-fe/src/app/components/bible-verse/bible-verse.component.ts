import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { BibleVerse } from '../../model/bible-verse';
import { SelectedBibleBook } from '../../store/site-state';
import { BibleBook } from '../../model/bible-book';
import { HtmlFunctions } from '../utility/html-functions';
import { BibleToastrService } from '../utility/jetty-toastr-service/bible-toastr.service';
import { NgClass } from '@angular/common';
import { BibleVerseHighlighterComponent } from '../bible-verse-highlighter/bible-verse-highlighter.component';

@Component({
  selector: 'bible-bible-verse',
  standalone: true,
  templateUrl: './bible-verse.component.html',
  styleUrl: './bible-verse.component.scss',
  imports: [NgClass, BibleVerseHighlighterComponent],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BibleVerseComponent {
  @Input({ required: true }) bibleVerse: BibleVerse | null = null;
  @Input() selectedBook: SelectedBibleBook | null = null;
  @Input() bibleBooks: BibleBook[] | null = null;
  @Input() highlightWords: string[] = [];
  @Input() highlightWholeVerse = false;
  @Input() hasJumpSection = false;

  constructor(private readonly bibleToastrService: BibleToastrService) {}

  protected copyTextToClipboard() {
    const completeVerse = this.getCompleteVerse();
    completeVerse && HtmlFunctions.copyTextToClipboard(completeVerse);
  }

  private displayCopyMessage(verseInfo: string) {
    this.bibleToastrService.info(verseInfo, 'Verset copiat', false, 1000);
  }

  private getVerseText(): string {
    const verse = this.bibleVerse;
    if (!verse) return '';

    return verse.textWithDiacritics;
  }

  private getVerseFooterDetails(): string {
    const books = this.bibleBooks;
    const selectedBook = this.selectedBook;
    const verse = this.bibleVerse;

    if (!verse) {
      return '';
    }

    if (verse.chapter) {
      return `(${verse.chapter.book.abbreviation} ${verse.chapter.number}:${verse.verseNumber})`;
    }

    if (!selectedBook) {
      return '';
    }

    if (!books) {
      return `(${selectedBook.bookName} ${selectedBook.chapterNumber}:${verse.verseNumber})`;
    }

    const bibleBook = books.find((book) => book.bookId === selectedBook.bookId);
    if (!bibleBook) return '';

    return `(${bibleBook.abbreviation} ${selectedBook.chapterNumber}:${verse.verseNumber})`;
  }

  private getCompleteVerse(): string | null {
    const verseText = this.getVerseText();
    if (verseText.length === 0) return null;

    const verseInfo = this.getVerseFooterDetails();
    verseInfo.length > 0 && this.displayCopyMessage(verseInfo);
    return `${verseText}\n${verseInfo}`;
  }
}
