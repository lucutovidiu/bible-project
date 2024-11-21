import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BibleVerseEditorComponent } from './bible-verse-editor.component';

describe('BibleVerseEditorComponent', () => {
  let component: BibleVerseEditorComponent;
  let fixture: ComponentFixture<BibleVerseEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [BibleVerseEditorComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BibleVerseEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
