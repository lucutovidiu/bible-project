import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { NgOptimizedImage } from '@angular/common';
import {
  FormBuilder,
  FormControl,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

import { NavBarService } from '../../services/nav-bar-service/nav-bar.service';

@Component({
  selector: 'bible-nav-bar',
  standalone: true,
  imports: [NgOptimizedImage, ReactiveFormsModule, RouterLink],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NavBarComponent implements OnInit {
  searchForm = this.fb.group({
    searchBox: new FormControl<string | null>(null, {
      validators: [Validators.required],
      nonNullable: true,
    }),
  });

  constructor(
    private readonly fb: FormBuilder,
    private readonly router: Router,
    private readonly navBarService: NavBarService,
  ) {
    this.navBarService.navBarSearchBox$
      .pipe(takeUntilDestroyed())
      .subscribe((searchText) => {
        this.searchForm.controls.searchBox.patchValue(searchText, {
          emitEvent: false,
          onlySelf: true,
        });
      });
  }

  ngOnInit(): void {
    this.searchForm.controls.searchBox.valueChanges.subscribe((searchText) => {
      if (searchText) {
        this.navBarService.updateNavbarSearchBox(searchText);
      }
    });
  }

  search() {
    const searchTerm = this.searchForm.controls.searchBox.value;
    if (searchTerm && searchTerm.length > 2) {
      this.router.navigate(['/search/' + searchTerm]).catch(console.error);
    }
  }
}
