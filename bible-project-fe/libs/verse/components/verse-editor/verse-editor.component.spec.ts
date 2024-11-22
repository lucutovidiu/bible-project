import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerseEditorComponent } from './verse-editor.component';

describe('BibleVerseEditorComponent', () => {
  let component: VerseEditorComponent;
  let fixture: ComponentFixture<VerseEditorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VerseEditorComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(VerseEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
