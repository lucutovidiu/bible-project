import { Injectable } from '@angular/core';

import { SitePreferencesStoreService } from '../../store/site-preferences/site-preferences-store.service';
import { SitePreferencesQueryService } from '../../store/site-preferences/site-preferences-query.service';
import { Observable } from 'rxjs';
import { SettingsPageState } from '../../store/site-preferences/site-preferences-state';

@Injectable({
  providedIn: 'root',
})
export class SettingsService {
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

  updateFatherName(FathersName: string) {
    this.sitePreferencesStoreService.updateFatherName(FathersName);
  }

  updateSonsName(SonsName: string) {
    this.sitePreferencesStoreService.updateSonsName(SonsName);
  }
}
