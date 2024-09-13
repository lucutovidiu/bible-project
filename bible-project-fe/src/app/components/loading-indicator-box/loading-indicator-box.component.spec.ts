import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoadingIndicatorBoxComponent } from './loading-indicator-box.component';

describe('LoadingIndicatorBoxComponent', () => {
  let component: LoadingIndicatorBoxComponent;
  let fixture: ComponentFixture<LoadingIndicatorBoxComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoadingIndicatorBoxComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(LoadingIndicatorBoxComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
