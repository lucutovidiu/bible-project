import { Injectable } from '@angular/core';
import { Query } from '@datorama/akita';
import { Observable } from 'rxjs';

import {
  SettingsPageState,
  SitePreferencesState,
} from './site-preferences-state';
import { SitePreferencesStoreService } from './site-preferences-store.service';
import { SelectedBibleBook } from '../site-state';

@Injectable({
  providedIn: 'root',
})
export class SitePreferencesQueryService extends Query<SitePreferencesState> {
  readonly homePageSelectedBibleBook$: Observable<SelectedBibleBook> =
    this.select((state) => state.homePage.selectedBibleBook);
  readonly navBarSearchBox$: Observable<string> = this.select(
    (state) => state.navbar.searchText,
  );
  readonly settings$: Observable<SettingsPageState> = this.select('settings');

  constructor(protected override store: SitePreferencesStoreService) {
    super(store);
  }
}
