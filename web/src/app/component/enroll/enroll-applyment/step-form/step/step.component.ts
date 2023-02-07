import { Component, Input, HostBinding, ViewChild, TemplateRef } from '@angular/core';

@Component({
  selector: 'app-step',
  templateUrl: './step.component.html',
  styleUrls: ['./step.component.less'],
})
export class StepComponent {
  @Input()
  @HostBinding('attr.name')
  name = '';

  @Input()
  @HostBinding('attr.title')
  title = '';

  @Input()
  @HostBinding('attr.disabledMessage')
  disabledMessage = 'Сперва заполните все поля текущей формы';

  @Input()
  @HostBinding('attr.state')
  state : 'pass' | 'curr' | 'not_pass' = 'not_pass';

  @ViewChild('ref')
  readonly ref!: TemplateRef<any>;
}
