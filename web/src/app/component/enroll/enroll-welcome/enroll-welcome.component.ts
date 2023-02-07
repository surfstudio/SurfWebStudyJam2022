import { Component, Input, HostBinding, Output, EventEmitter } from '@angular/core';


@Component({
  selector: 'app-enroll-welcome',
  templateUrl: './enroll-welcome.component.html',
  styleUrls: ['./enroll-welcome.component.less'],
})
export class EnrollWelcomeComponent {
  @Input()
  @HostBinding('attr.eventTitle')
  eventTitle = 'Название стажировки';

  @Output()
  protected onApply: EventEmitter<void> = new EventEmitter();

  protected applyButtonClicked(): void {
    this.onApply.emit();
  }
}
