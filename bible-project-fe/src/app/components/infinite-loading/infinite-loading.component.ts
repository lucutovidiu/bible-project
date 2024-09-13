import { ChangeDetectionStrategy, Component, Input } from '@angular/core';

import { MakeModalDirective } from '../../directives/make-modal.directive';

@Component({
  standalone: true,
  imports: [MakeModalDirective, MakeModalDirective],
  selector: 'bible-infinite-loading',
  templateUrl: './infinite-loading.component.html',
  styleUrls: ['./infinite-loading.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class InfiniteLoadingComponent {
  @Input() loadingText = '';
}
