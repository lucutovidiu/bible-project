import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultDisplayFullChapterComponent } from './result-display-full-chapter.component';

describe('ResultDisplayFullChapterComponent', () => {
  let component: ResultDisplayFullChapterComponent;
  let fixture: ComponentFixture<ResultDisplayFullChapterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ResultDisplayFullChapterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResultDisplayFullChapterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
