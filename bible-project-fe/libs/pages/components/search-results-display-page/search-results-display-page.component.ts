import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AsyncPipe, NgClass, NgForOf, NgIf } from '@angular/common';

import { LoadingIndicatorBoxComponent } from '@bible/shared';
import { VerseComponent } from '@bible/verse';

import { SearchPageService } from '../../services/search-page-service/search-page.service';

@Component({
  selector: 'bible-search-results-display-page',
  standalone: true,
  templateUrl: './search-results-display-page.component.html',
  styleUrl: './search-results-display-page.component.scss',
  imports: [
    NgIf,
    AsyncPipe,
    NgForOf,
    NgClass,
    RouterLink,
    LoadingIndicatorBoxComponent,
    VerseComponent,
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SearchResultsDisplayPageComponent implements OnInit {
  protected readonly bibleVerses$ =
    this.searchPageService.searchPageBibleVerseResult$;
  protected searchTextWords: string[] = [];
  protected readonly searchPageLoading$ =
    this.searchPageService.searchPageLoading$;

  constructor(
    private readonly searchPageService: SearchPageService,
    private readonly route: ActivatedRoute,
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe((params) => {
      const searchText = params.get('searchText');
      if (searchText) {
        this.searchTextWords = searchText.split(' ');
      }
      this.search(searchText);
    });
  }

  search(searchText: string | null) {
    if (searchText && searchText.length > 2) {
      this.searchPageService.findPlacesInTheBibleByVerseText(searchText);
    }
  }
}
