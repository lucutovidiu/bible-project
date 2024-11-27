import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { AsyncPipe, NgClass } from '@angular/common';
import { BehaviorSubject } from 'rxjs';

import {
  BibleBook,
  BibleToastrService,
  BibleVerse,
  BookEditInfo,
  buildBookEditInfo,
  ClipboardCopyService,
  SelectedBibleBook,
} from '@bible/shared';

import { VerseEditorComponent } from '../verse-editor/verse-editor.component';
import { VerseOptionsComponent } from '../verse-options/verse-options.component';
import { VerseComponent } from '../verse/verse.component';

@Component({
  selector: 'bible-verse-manager',
  standalone: true,
  templateUrl: './verse-manager.component.html',
  styleUrl: './verse-manager.component.scss',
  imports: [
    VerseEditorComponent,
    VerseOptionsComponent,
    NgClass,
    VerseComponent,
    AsyncPipe,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class VerseManagerComponent {
  @Input({ required: true }) bibleVerse: BibleVerse | null = null;
  @Input() selectedBook: SelectedBibleBook | null = null;
  @Input() bibleBooks: BibleBook[] | null = null;
  @Input() highlightWords: string[] = [];
  @Input() highlightWholeVerse = false;
  @Input() hasJumpSection = false;

  protected shouldEditVerse = false;
  protected shouldDisplayVerseOptions$ = new BehaviorSubject<boolean>(false);
  private clickTimeout: number[] = [];

  constructor(private readonly bibleToastrService: BibleToastrService) {}

  protected copyTextToClipboard() {
    this.clearAllIntervals();
    new ClipboardCopyService(
      this.getBookEditInfo(),
      this.bibleToastrService,
    ).copyTextToClipboard();
  }

  protected getBookEditInfo(): BookEditInfo | null {
    return buildBookEditInfo(
      this.bibleBooks,
      this.selectedBook,
      this.bibleVerse,
    );
  }

  protected displayVerseOptions() {
    this.clickTimeout.push(
      setTimeout(() => {
        this.shouldDisplayVerseOptions$.next(
          !this.shouldDisplayVerseOptions$.value,
        );
      }, 200),
    );
  }

  protected textUpdate(textWithDiacritics: string) {
    this.shouldDisplayVerseOptions$.next(
      !this.shouldDisplayVerseOptions$.value,
    );
    this.shouldEditVerse = !this.shouldEditVerse;
    this.updateBibleVerse(textWithDiacritics);
  }

  private clearAllIntervals() {
    this.clickTimeout.forEach(clearTimeout);
    this.clickTimeout = [];
  }

  private updateBibleVerse(textWithDiacritics: string) {
    this.bibleVerse = {
      verseNumber: this.bibleVerse?.verseNumber,
      text: this.bibleVerse?.text,
      textWithDiacritics: textWithDiacritics,
      chapter: this.bibleVerse?.chapter,
    } as BibleVerse;
  }
}
