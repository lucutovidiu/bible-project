import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerseManagerComponent } from './verse-manager.component';

describe('BibleVerseComponent', () => {
  let component: VerseManagerComponent;
  let fixture: ComponentFixture<VerseManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VerseManagerComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(VerseManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
