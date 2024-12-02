import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';
import { Observable } from 'rxjs';
import { AsyncPipe } from '@angular/common';

import { BookEditInfo, ClipboardCopyService } from '@bible/shared';
import { SettingsPageService } from '@bible/pages';

@Component({
  selector: 'bible-verse-options',
  standalone: true,
  templateUrl: './verse-options.component.html',
  styleUrl: './verse-options.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [AsyncPipe],
})
export class VerseOptionsComponent {
  @Input({ required: true }) bookEditInfo: BookEditInfo | null = null;
  @Input() isVerseInEditMode: boolean = false;
  @Output() shouldEnterEditMode: EventEmitter<void> = new EventEmitter();
  @Output() shouldCloseOptions: EventEmitter<boolean> = new EventEmitter();

  protected readonly isApiKeySet$: Observable<boolean> =
    this.settingsServiceService.isApiKeySet$;

  constructor(
    private readonly clipboardCopyService: ClipboardCopyService,
    private readonly settingsServiceService: SettingsPageService,
  ) {}

  protected enterEditMode() {
    this.shouldEnterEditMode.emit();
  }

  protected copyTextToClipboard() {
    this.clipboardCopyService.copyTextToClipboard(this.bookEditInfo);
    this.shouldCloseOptions.emit(true);
  }
}
