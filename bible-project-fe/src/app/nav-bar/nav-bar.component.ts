import {ChangeDetectionStrategy, Component} from '@angular/core';
import {NgOptimizedImage} from "@angular/common";
import {FormBuilder, FormControl, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'bible-nav-bar',
  standalone: true,
  imports: [
    NgOptimizedImage,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NavBarComponent {
  searchForm = this.fb.group({
    searchBox: new FormControl<string | null>(null, {
      validators: [
        Validators.required,
      ],
      nonNullable: true,
    }),
  });

  constructor(private readonly fb: FormBuilder,
              private readonly router: Router,) {
  }

  search() {
    const searchTerm = this.searchForm.controls.searchBox.value;
    if (searchTerm && searchTerm.length > 2) {
      this.router.navigate(['/search/'+searchTerm])
        .catch(console.error);
    }
  }
}
