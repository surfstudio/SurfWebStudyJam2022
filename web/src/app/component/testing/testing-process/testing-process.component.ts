import { Component, OnInit, Input, HostBinding, Output, EventEmitter, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { Observable, Subject, timer } from 'rxjs';
import { takeWhile, finalize, map, startWith } from 'rxjs/operators';

import { TestInfo } from 'entity/test-info';
import { TestingService } from 'service/testing.service';
import { Answer } from 'entity/answer'

@Component({
  selector: 'app-testing-process',
  templateUrl: './testing-process.component.html',
  styleUrls: ['./testing-process.component.less'],
})
export class TestingProcessComponent implements OnInit {
  @Input()
  @HostBinding('attr.maxAcceptableDurationSec')
  maxAcceptableDurationSec: number = 1;

  @Input()
  @HostBinding('attr.finishingAt')
  finishingAt: Date | null = null;

  @Input()
  @HostBinding('attr.testVariantId')
  testVariantId: string = "";

  @Output()
  protected onApply: EventEmitter<void> = new EventEmitter();

  protected readonly testInfo$ = new Subject<TestInfo>();

  protected readonly testInfoObservable$: Observable<TestInfo> =
    this.testInfo$.pipe(
      takeWhile(t => t.currentQuestion != null),
      finalize(() => this.testingService.getTestInfo(this.testVariantId).subscribe(it => this.onApply.emit()))
    );

	readonly testTimerValue$: Observable<number> = timer(0, 300).pipe(
    map(_ => this.finishingAt ? (new Date(this.finishingAt).getTime() - new Date().getTime()) / 1000 : 0 ),
    takeWhile(t => t > 0),
    finalize(() => this.testingService.getTestInfo(this.testVariantId).subscribe(it => this.onApply.emit()))
  );

  constructor(
    @Inject(TestingService)
    private testingService: TestingService
  ) { }

  ngOnInit(): void {
    this.testingService.getTestInfo(this.testVariantId).subscribe(it => this.testInfo$.next(it))
  }

  testingForm = new FormGroup({
    chosenVariant: new FormControl('', [
      Validators.required
    ]),
  });

  submitCurrentAnswerAndMoveNext(testInfo: TestInfo) {
    const answer = {
      testVariantId: testInfo.testVariantId,
      chosenAnswersId: this.getChosenAnswers(testInfo)
    }
    this.testingService.submitCurrentAnswerAndMoveNext(answer).subscribe(result => {
      console.log(result);
      this.testInfo$.next(result);
    }, error => {
      console.log(error);
    })
  }

  protected getChosenAnswers(testInfo: TestInfo): Array<string> {
    if (testInfo.currentQuestion!.questionType == 'MULTIPLE_CHOICE') {
      return testInfo.
        currentQuestion!.
        answers.
          filter(it => it.value).
          map(it => it.answerId);
    }
    if (testInfo.currentQuestion!.questionType == "SINGLE_CHOICE") {
      return testInfo.
        currentQuestion!.
        answers.
          filter(it => it.answerId == this.testingForm.controls.chosenVariant.value).
          map(it => it.answerId);
    }
    return [];
  }
}
