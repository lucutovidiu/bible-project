import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThreeDButtonComponent } from './three-d-button.component';

describe('ThreeDButtonComponent', () => {
  let component: ThreeDButtonComponent;
  let fixture: ComponentFixture<ThreeDButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ThreeDButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ThreeDButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
