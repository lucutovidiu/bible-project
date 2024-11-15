import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BibleVerseHighlighterComponent } from './bible-verse-highlighter.component';

describe('BibleVerseHighlighterComponent', () => {
  let component: BibleVerseHighlighterComponent;
  let fixture: ComponentFixture<BibleVerseHighlighterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BibleVerseHighlighterComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BibleVerseHighlighterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
