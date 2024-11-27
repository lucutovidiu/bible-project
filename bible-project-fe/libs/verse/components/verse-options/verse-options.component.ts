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
import { ClipboardCopyService } from '../../../shared/services/clipboard-copy/clipboard-copy.service';

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

  protected enterEditMode() {
    this.shouldEnterEditMode.emit();
  }

  protected copyTextToClipboard() {
    new ClipboardCopyService(
      this.bookEditInfo,
      this.bibleToastrService,
    ).copyTextToClipboard();
    this.shouldCloseOptions.emit();
  }
}
