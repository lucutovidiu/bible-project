import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerseOptionsComponent } from './verse-options.component';

describe('BibleVerseComponent', () => {
  let component: VerseOptionsComponent;
  let fixture: ComponentFixture<VerseOptionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VerseOptionsComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(VerseOptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
