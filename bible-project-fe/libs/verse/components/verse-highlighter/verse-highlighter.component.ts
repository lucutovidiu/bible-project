import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { NgClass } from '@angular/common';

import { BibleVerse } from '@bible/shared';

@Component({
  standalone: true,
  selector: 'bible-verse-highlighter',
  templateUrl: './verse-highlighter.component.html',
  styleUrl: './verse-highlighter.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [NgClass],
})
export class VerseHighlighterComponent {
  @Input({ required: true }) bibleVerse: BibleVerse | null = null;
  @Input() highlightWords: string[] = [];

  protected shouldColoredTheText(index: number): boolean {
    const textWithoutDiacritics = this.bibleVerse?.text || null;
    if (!textWithoutDiacritics) return false;

    return this.highlightWords.some((item) =>
      textWithoutDiacritics
        .split(' ')
        [index].toLowerCase()
        .includes(item.toLowerCase()),
    );
  }
  protected readonly Math = Math;
}
