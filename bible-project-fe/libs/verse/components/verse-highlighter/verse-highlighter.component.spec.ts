import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerseHighlighterComponent } from './verse-highlighter.component';

describe('BibleVerseHighlighterComponent', () => {
  let component: VerseHighlighterComponent;
  let fixture: ComponentFixture<VerseHighlighterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VerseHighlighterComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(VerseHighlighterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
