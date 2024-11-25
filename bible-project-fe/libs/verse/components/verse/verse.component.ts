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
import { VerseOptionsComponent } from '../verse-options/verse-options.component';

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
    VerseOptionsComponent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class VerseComponent {
  @Input({ required: true }) bibleVerse!: BibleVerse;
  @Input() highlightWords: string[] = [];
  @Input() highlightWholeVerse = false;
  @Input() hasJumpSection = false;
}
