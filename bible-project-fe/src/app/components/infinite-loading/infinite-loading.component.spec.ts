import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfiniteLoadingComponent } from './infinite-loading.component';
import { MockDirective } from 'ng-mocks';
import { MakeModalDirective } from '../../directives/make-modal.directive';

describe('InfiniteLoadingComponent', () => {
  let component: InfiniteLoadingComponent;
  let fixture: ComponentFixture<InfiniteLoadingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [
        InfiniteLoadingComponent,
        MockDirective(MakeModalDirective),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(InfiniteLoadingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
