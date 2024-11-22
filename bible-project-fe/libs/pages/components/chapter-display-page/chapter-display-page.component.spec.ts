import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChapterDisplayPageComponent } from './chapter-display-page.component';

describe('ResultDisplayFullChapterComponent', () => {
  let component: ChapterDisplayPageComponent;
  let fixture: ComponentFixture<ChapterDisplayPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ChapterDisplayPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ChapterDisplayPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
