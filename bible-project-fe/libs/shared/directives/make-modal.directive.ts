import {
  Directive,
  Input,
  OnInit,
  TemplateRef,
  ViewContainerRef,
} from '@angular/core';

import { ModalWrapperComponent } from '../components/modal-wrapper/modal-wrapper.component';

@Directive({
  standalone: true,
  selector: '[bibleMakeModal]',
})
export class MakeModalDirective implements OnInit {
  @Input() maxWidth!: string;
  @Input() backgroundColor!: string;

  private modalComponentRef = this.viewContainerRef.createComponent(
    ModalWrapperComponent,
  );
  constructor(
    private readonly templateRef: TemplateRef<unknown>,
    private readonly viewContainerRef: ViewContainerRef,
  ) {}

  ngOnInit(): void {
    this.modalComponentRef.instance.template = this.templateRef;
    this.modalComponentRef.instance.setMaxWidth(this.maxWidth);
    this.modalComponentRef.instance.setBackgroundColor(this.backgroundColor);
  }
}
