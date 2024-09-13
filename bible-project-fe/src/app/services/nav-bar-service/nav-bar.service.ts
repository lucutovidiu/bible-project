import {Injectable} from '@angular/core';

import {SiteStoreService} from "../../store/site-store.service";
import {SiteQueryService} from "../../store/site-query.service";

@Injectable({
  providedIn: 'root'
})
export class NavBarService {
  constructor(private readonly siteStoreService: SiteStoreService,
              private readonly siteQueryService: SiteQueryService,) {
  }

  get navBarSearchBox$() {
    return this.siteQueryService.navBarSearchBox$;
  }

  updateNavbarSearchBox(searchText: string) {
    this.siteStoreService.updateNavbarSearchBox(searchText);
  }

}
