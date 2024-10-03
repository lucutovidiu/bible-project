import { Component, DestroyRef, inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { SettingsService } from '../../services/settings-page-service/settings.service';

@Component({
  selector: 'bible-settings-page',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './settings-page.component.html',
  styleUrl: './settings-page.component.scss',
})
export class SettingsPageComponent implements OnInit {
  namesForm = this.fb.group({
    FathersName: new FormControl<string>(SettingsService.FATHERS_DEFAULT_NAME, {
      validators: [Validators.required],
      nonNullable: true,
    }),
    SonsName: new FormControl<string>(SettingsService.SONS_DEFAULT_NAME, {
      validators: [Validators.required],
      nonNullable: true,
    }),
  });
  protected readonly SettingsService = SettingsService;
  private readonly destroyRef: DestroyRef = inject(DestroyRef);

  constructor(
    private readonly fb: FormBuilder,
    private readonly settingsServiceService: SettingsService,
  ) {}

  get formControl() {
    return this.namesForm.controls;
  }

  ngOnInit(): void {
    this.formControl.FathersName.valueChanges
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((FathersName) => {
        this.settingsServiceService.updateFatherName(FathersName);
      });
    this.formControl.SonsName.valueChanges
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe((SonsName) => {
        this.settingsServiceService.updateSonsName(SonsName);
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
      });
  }
}
