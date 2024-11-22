import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchResultsDisplayPageComponent } from './search-results-display-page.component';

describe('SearchComponent', () => {
  let component: SearchResultsDisplayPageComponent;
  let fixture: ComponentFixture<SearchResultsDisplayPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SearchResultsDisplayPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SearchResultsDisplayPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
