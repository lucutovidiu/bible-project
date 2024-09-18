import { Injectable } from '@angular/core';
import { SitePreferencesQueryService } from '../../store/site-preferences/site-preferences-query.service';
import { SitePreferencesStoreService } from '../../store/site-preferences/site-preferences-store.service';

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
