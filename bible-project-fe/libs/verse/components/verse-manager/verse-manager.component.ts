import {
  ChangeDetectionStrategy,
  Component,
  Input,
  OnInit,
} from '@angular/core';
import { JsonPipe, NgClass } from '@angular/common';

import {
  BibleBook,
  BibleVerse,
  BookEditInfo,
  buildBookEditInfo,
  SelectedBibleBook,
} from '@bible/shared';

import { VerseEditorComponent } from '../verse-editor/verse-editor.component';
import { VerseHighlighterComponent } from '../verse-highlighter/verse-highlighter.component';
import { VerseOptionsComponent } from '../verse-options/verse-options.component';
import { VerseComponent } from '../verse/verse.component';

@Component({
  selector: 'bible-verse-manager',
  standalone: true,
  templateUrl: './verse-manager.component.html',
  styleUrl: './verse-manager.component.scss',
  imports: [
    VerseEditorComponent,
    VerseHighlighterComponent,
    VerseOptionsComponent,
    NgClass,
    VerseComponent,
    JsonPipe,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class VerseManagerComponent implements OnInit {
  @Input({ required: true }) bibleVerse: BibleVerse | null = null;
  @Input() selectedBook: SelectedBibleBook | null = null;
  @Input() bibleBooks: BibleBook[] | null = null;
  @Input() highlightWords: string[] = [];
  @Input() highlightWholeVerse = false;
  @Input() hasJumpSection = false;

  protected bookEditInfo: BookEditInfo | null = null;
  protected shouldEditVerse = false;
  protected shouldDisplayVerseOptions = false;

  ngOnInit() {
    this.bookEditInfo = buildBookEditInfo(
      this.bibleBooks,
      this.selectedBook,
      this.bibleVerse,
    );
  }

  protected displayVerseOptions() {
    this.shouldDisplayVerseOptions = !this.shouldDisplayVerseOptions;
  }

  protected textUpdate(textWithDiacritics: string) {
    this.shouldDisplayVerseOptions = !this.shouldDisplayVerseOptions;
    this.shouldEditVerse = !this.shouldEditVerse;
    this.updateBibleVerse(textWithDiacritics);
  }

  private updateBibleVerse(textWithDiacritics: string) {
    this.bibleVerse = {
      verseNumber: this.bibleVerse?.verseNumber,
      text: this.bibleVerse?.text,
      textWithDiacritics: textWithDiacritics,
      chapter: this.bibleVerse?.chapter,
    } as BibleVerse;

    this.bookEditInfo = buildBookEditInfo(
      this.bibleBooks,
      this.selectedBook,
      this.bibleVerse,
    );
  }
}