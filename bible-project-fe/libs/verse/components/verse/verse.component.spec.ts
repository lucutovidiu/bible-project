import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerseComponent } from './verse.component';

describe('BibleVerseComponent', () => {
  let component: VerseComponent;
  let fixture: ComponentFixture<VerseComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VerseComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(VerseComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
