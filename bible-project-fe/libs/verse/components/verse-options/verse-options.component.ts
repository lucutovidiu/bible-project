import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';
import {
  BibleToastrService,
  BookEditInfo,
  HtmlFunctions,
  buildVerseFooterDetails,
} from '@bible/shared';

@Component({
  selector: 'bible-verse-options',
  standalone: true,
  templateUrl: './verse-options.component.html',
  styleUrl: './verse-options.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class VerseOptionsComponent {
  @Input({ required: true }) bookEditInfo: BookEditInfo | null = null;
  @Input() isVerseInEditMode: boolean = false;
  @Output() shouldEnterEditMode: EventEmitter<void> = new EventEmitter();
  @Output() shouldCloseOptions: EventEmitter<void> = new EventEmitter();

  constructor(private readonly bibleToastrService: BibleToastrService) {}

  protected copyTextToClipboard() {
    const completeVerse = this.getCompleteVerse();
    completeVerse && HtmlFunctions.copyTextToClipboard(completeVerse);
    this.shouldCloseOptions.emit();
  }

  private getCompleteVerse(): string | null {
    const bookEditInfo = this.bookEditInfo;
    if (!bookEditInfo) {
      return null;
    }
    const verseInfo = buildVerseFooterDetails(bookEditInfo);
    verseInfo.length > 0 && this.displayCopyMessage(verseInfo);

    return `${bookEditInfo.textWithDiacritics}\n${verseInfo}`;
  }

  private displayCopyMessage(verseInfo: string) {
    this.bibleToastrService.info(verseInfo, 'Verset copiat', false, 1000);
  }

  enterEditMode() {
    this.shouldEnterEditMode.emit();
  }
}
