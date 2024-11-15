import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookChapterPageComponent } from './book-chapter-page.component';

describe('ResultDisplayFullChapterComponent', () => {
  let component: BookChapterPageComponent;
  let fixture: ComponentFixture<BookChapterPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BookChapterPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BookChapterPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
