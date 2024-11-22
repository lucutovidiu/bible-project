import { Injectable } from '@angular/core';

import {
  SitePreferencesQueryService,
  SitePreferencesStoreService,
} from '../../store/';

@Injectable({
  providedIn: 'root',
})
export class NavBarService {
  constructor(
    private readonly sitePreferencesQueryService: SitePreferencesQueryService,
    private readonly sitePreferencesStoreService: SitePreferencesStoreService,
  ) {}

  get navBarSearchBox$() {
    return this.sitePreferencesQueryService.navBarSearchBox$;
  }

  updateNavbarSearchBox(searchText: string) {
    this.sitePreferencesStoreService.updateNavbarSearchBox(searchText);
  }
}
