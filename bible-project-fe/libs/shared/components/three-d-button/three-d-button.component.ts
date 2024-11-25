import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'bible-three-d-button',
  standalone: true,
  imports: [],
  templateUrl: './three-d-button.component.html',
  styleUrl: './three-d-button.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ThreeDButtonComponent {}
