import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  inject,
  OnInit,
} from '@angular/core';
import {
  FormBuilder,
  FormControl,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { SettingsPageService } from '@bible/pages';
import { SiteStoreService } from '@bible/shared';
import { InterceptorStorage } from '../../../shared/services/interceptor/interceptor-storage';

@Component({
  selector: 'bible-settings-page',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './settings-page.component.html',
  styleUrl: './settings-page.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SettingsPageComponent implements OnInit {
  namesForm = this.fb.group({
    FathersName: new FormControl<string>(
      SettingsPageService.FATHERS_DEFAULT_NAME,
      {
        validators: [Validators.required],
        nonNullable: true,
      },
    ),
    SonsName: new FormControl<string>(SettingsPageService.SONS_DEFAULT_NAME, {
      validators: [Validators.required],
      nonNullable: true,
    }),
    apiKey: new FormControl<string>('', {
      validators: [Validators.required],
      nonNullable: true,
    }),
  });
  // protected readonly SettingsService = SettingsPageService;
  private readonly destroyRef: DestroyRef = inject(DestroyRef);

  constructor(
    private readonly fb: FormBuilder,
    private readonly settingsServiceService: SettingsPageService,
    private readonly interceptorStorage: InterceptorStorage,
    private readonly siteStoreService: SiteStoreService,
  ) {}

  get formControl() {
    return this.namesForm.controls;
  }

  ngOnInit(): void {
    // this.formControl.FathersName.valueChanges
    //   .pipe(takeUntilDestroyed(this.destroyRef))
    //   .subscribe((FathersName) => {
    //     this.settingsServiceService.updateFatherName(FathersName);
    //     this.clearStoredVerses();
    //   });
    // this.formControl.SonsName.valueChanges
    //   .pipe(takeUntilDestroyed(this.destroyRef))
    //   .subscribe((SonsName) => {
    //     this.settingsServiceService.updateSonsName(SonsName);
    //     this.clearStoredVerses();
    //   });
    this.formControl.apiKey.valueChanges
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((apiKey) => {
        this.interceptorStorage.storeApiKey(apiKey);
        this.settingsServiceService.updateApiKey(apiKey);
      });
    this.settingsServiceService.settings$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((settings) => {
        this.formControl.SonsName.patchValue(settings.SonsName, {
          onlySelf: true,
          emitEvent: false,
        });
        this.formControl.FathersName.patchValue(settings.FathersName, {
          onlySelf: true,
          emitEvent: false,
        });
        this.formControl.apiKey.patchValue(settings.apiKey, {
          onlySelf: true,
          emitEvent: false,
        });
      });
  }

  resetWebsiteSettings() {
    this.siteStoreService.reset();
    this.settingsServiceService.resetStore();
  }

  private clearStoredVerses() {
    this.siteStoreService.resetStoredBibleVerses();
  }
}
