import { ChangeDetectionStrategy, Component, Input } from '@angular/core';
import { BibleVerse } from '../../model/bible-verse';
import { NgClass } from '@angular/common';

@Component({
  standalone: true,
  selector: 'bible-bible-verse-highlighter',
  templateUrl: './bible-verse-highlighter.component.html',
  styleUrl: './bible-verse-highlighter.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [NgClass],
})
export class BibleVerseHighlighterComponent {
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
