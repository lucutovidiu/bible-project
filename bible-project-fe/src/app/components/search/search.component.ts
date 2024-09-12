import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from "@angular/router";
import {BehaviorSubject} from "rxjs";
import {AsyncPipe, NgClass, NgForOf, NgIf} from "@angular/common";

import {BibleBookService} from "../../services/bible-book/bible-book.service";
import {BibleVerse} from "../../services/dto/bible-verse";

@Component({
  selector: 'bible-search',
  standalone: true,
  templateUrl: './search.component.html',
  styleUrl: './search.component.scss',
  imports: [
    NgIf,
    AsyncPipe,
    NgForOf,
    NgClass,
    RouterLink
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchComponent implements OnInit {
  protected readonly bibleVerses$ = new BehaviorSubject<BibleVerse[]>([]);
  protected searchTextWords: string[] = [];

  constructor(private readonly bibleBookService: BibleBookService,
              private readonly route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const searchText = params.get('searchText');
      if (searchText) {
        this.searchTextWords = searchText.split(' ');
      }
      this.search(searchText);
    });
  }

  shouldColoredTheText(inputWord: string): boolean {
    return this.searchTextWords.some(item => inputWord.toLowerCase().includes(item.toLowerCase()));
  }

  search(searchText: string | null) {
    if (searchText && searchText.length > 2) {
      this.bibleBookService.findPlacesInTheBibleByVerseText(searchText)
        .subscribe(bibleVerses => this.bibleVerses$.next(bibleVerses))
    }
  }

  copyTextToClipboard(bibleVerse: BibleVerse) {
    const text = `${bibleVerse.textWithDiacritics}\n(${bibleVerse.chapter.book.name} ${bibleVerse.chapter.number}:${bibleVerse.verseNumber})`;
    // Copy the text to the clipboard
    navigator.clipboard.writeText(text)
      .catch(console.error);
  }
}
