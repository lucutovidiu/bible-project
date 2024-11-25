import {
  ChangeDetectionStrategy,
  Component,
  Input,
  TemplateRef,
} from '@angular/core';
import { NgTemplateOutlet } from '@angular/common';

@Component({
  standalone: true,
  selector: 'bible-modal-wrapper',
  imports: [NgTemplateOutlet],
  templateUrl: './modal-wrapper.component.html',
  styleUrls: ['./modal-wrapper.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ModalWrapperComponent {
  @Input() template!: TemplateRef<unknown>;
  protected maxWidth!: string;
  protected backgroundColor!: string;

  setMaxWidth(maxWidth: string | null) {
    this.maxWidth = maxWidth || '400px';
  }
  setBackgroundColor(backgroundColor: string | null) {
    this.backgroundColor = backgroundColor || '#fff';
  }
}
