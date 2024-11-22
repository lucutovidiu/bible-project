import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import {
  SettingsPageState,
  SitePreferencesQueryService,
  SitePreferencesStoreService,
} from '@bible/shared';

@Injectable({
  providedIn: 'root',
})
export class SettingsPageService {
  public static FATHERS_DEFAULT_NAME = 'YaHWeH';
  public static FATHERS_DEFAULT_NAME_2 = 'Yahweh';
  public static YAHUAH = 'Yahuah';
  public static YAUE = 'Yaue';
  public static SONS_DEFAULT_NAME = 'Yașua';
  public static SONS_NEW_NAME = 'Yahusha';

  constructor(
    private readonly sitePreferencesStoreService: SitePreferencesStoreService,
    private readonly sitePreferencesQueryService: SitePreferencesQueryService,
  ) {}

  get settings$(): Observable<SettingsPageState> {
    return this.sitePreferencesQueryService.settings$;
  }

  resetStore() {
    this.sitePreferencesStoreService.reset();
  }

  updateFatherName(FathersName: string) {
    this.sitePreferencesStoreService.updateFatherName(FathersName);
  }

  updateSonsName(SonsName: string) {
    this.sitePreferencesStoreService.updateSonsName(SonsName);
  }
}
