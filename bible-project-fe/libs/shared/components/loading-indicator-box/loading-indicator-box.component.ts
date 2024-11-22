import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  Output,
} from '@angular/core';
import { CommonModule } from '@angular/common';

import { InfiniteLoadingComponent } from '../infinite-loading/infinite-loading.component';

@Component({
  selector: 'bible-loading-indicator-box',
  standalone: true,
  imports: [CommonModule, InfiniteLoadingComponent],
  templateUrl: './loading-indicator-box.component.html',
  styleUrls: ['./loading-indicator-box.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoadingIndicatorBoxComponent {
  @Input({ required: true, transform: nullToBoolean }) isLoading = false;
  @Input() errorTextMessage: string | null = null;
  @Input() errorBtnMessage: string | null = null;
  @Input() displayOnErrorComponent = false;
  @Input() overrideErrorMessage: string | null = null;
  @Output() errorBtnAction: EventEmitter<void> = new EventEmitter<void>();

  errorActionBtn() {
    this.errorBtnAction.emit();
  }
}

function nullToBoolean(value: boolean | null) {
  return value !== null ? value : false;
}
