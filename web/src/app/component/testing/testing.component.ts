import { Component, ChangeDetectionStrategy, isDevMode } from '@angular/core';
import { ActivatedRoute} from '@angular/router';

import { TestingService } from 'service/testing.service';
import { TestInfo } from 'entity/test-info';


@Component({
  selector: 'app-testing',
  templateUrl: './testing.component.html',
  styleUrls: ['./testing.component.less'],
})
export class TestingComponent {
  protected test?: TestInfo | null;

  protected readonly devMode = isDevMode();

  constructor(private testingService: TestingService, private activateRoute: ActivatedRoute) {
    const testVariantId = activateRoute.snapshot.params['testVariantId'];
    this.testingService.getTestInfo(testVariantId).subscribe(result => {
      this.test = result;
    }, error => {
      console.log(error);
    });
  }
}
