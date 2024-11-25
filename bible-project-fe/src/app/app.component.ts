import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'bible-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AppComponent {
  title = 'bible-project-fe';
}
