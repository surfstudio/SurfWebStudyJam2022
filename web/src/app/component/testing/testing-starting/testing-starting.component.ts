import { Component, Input, HostBinding, Output, EventEmitter } from '@angular/core';

import { TestInfo } from 'entity/test-info'
import { TestingService } from 'service/testing.service';


@Component({
  selector: 'app-testing-starting',
  templateUrl: './testing-starting.component.html',
  styleUrls: ['./testing-starting.component.less']
})
export class TestingStartingComponent {
  @Input()
  @HostBinding('attr.maxAcceptableDurationSec')
  maxAcceptableDurationSec = 0;

  @Input()
  @HostBinding('attr.testVariantId')
  testVariantId = "";

  @Output()
  protected onApply: EventEmitter<TestInfo> = new EventEmitter();

  constructor(private testingService: TestingService) {
  }

  protected applyButtonClicked(): void {
    this.testingService.startTest(this.testVariantId).subscribe(result => {
      this.onApply.emit(result);
    }, error => {
      console.log(error);
    })
  }
}
