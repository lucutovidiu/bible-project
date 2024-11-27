import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

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

  get isApiKeySet$(): Observable<boolean> {
    return this.sitePreferencesQueryService.settings$.pipe(
      map((p) => p.apiKey !== '' && p.apiKey.length > 10),
    );
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

  updateApiKey(apiKey: string) {
    this.sitePreferencesStoreService.updateApiKey(apiKey);
  }
}
