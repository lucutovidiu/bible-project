import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {ActivatedRoute, RouterLink} from "@angular/router";
import {AsyncPipe, NgClass, NgForOf, NgIf} from "@angular/common";

import {BibleVerse} from "../../model/bible-verse";
import {LoadingIndicatorBoxComponent} from "../loading-indicator-box/loading-indicator-box.component";
import {SearchPageService} from "../../services/search-page-service/search-page.service";

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
    RouterLink,
    LoadingIndicatorBoxComponent
  ],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchComponent implements OnInit {
  protected readonly bibleVerses$ = this.searchPageService.searchPageBibleVerseResult$
  protected searchTextWords: string[] = [];
  protected readonly searchPageLoading$ = this.searchPageService.searchPageLoading$;

  constructor(private readonly searchPageService: SearchPageService,
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
      this.searchPageService.findPlacesInTheBibleByVerseText(searchText);
    }
  }

  copyTextToClipboard(bibleVerse: BibleVerse) {
    const text = `${bibleVerse.textWithDiacritics}\n(${bibleVerse.chapter.book.name} ${bibleVerse.chapter.number}:${bibleVerse.verseNumber})`;
    // Copy the text to the clipboard
    navigator.clipboard.writeText(text)
      .catch(console.error);
  }

  protected readonly Math = Math;
}
