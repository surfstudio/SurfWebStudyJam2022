import { Component, ChangeDetectionStrategy, ContentChildren, forwardRef, QueryList, Input, HostBinding, Inject, ChangeDetectorRef, TemplateRef } from '@angular/core';

import { TuiAlertService, TuiNotification } from '@taiga-ui/core';

import { StepComponent } from './step/step.component';

@Component({
  selector: 'app-step-form',
  templateUrl: './step-form.component.html',
  styleUrls: ['./step-form.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StepFormComponent {
  @ContentChildren(forwardRef(() => StepComponent), {read: StepComponent})
  protected readonly steps: QueryList<StepComponent> = new QueryList<StepComponent>();

  @Input()
  @HostBinding('attr.disableStepsTab')
  disableStepsTab: boolean = true;

  protected currentStepIndex: number = 0;

  constructor(
    @Inject(ChangeDetectorRef)
    private readonly changeDetectorRef: ChangeDetectorRef,

    @Inject(TuiAlertService)
    private readonly alertService: TuiAlertService
  ) {}

  getCurrentStep(): StepComponent | undefined {
    return this.steps.find((_, i) => this.currentStepIndex == i);
  }

  moveToNextStep(): void {
    const step = this.getCurrentStep();
    if (step) {
      step.state = 'pass';
    }

    this.moveToStep(1);

    const newStep = this.getCurrentStep();
    if (newStep && newStep.state == 'not_pass') {
      newStep.state = 'curr';
    }
  }

  moveToPrevStep(): void {
    this.moveToStep(-1);
  }

  protected clickOnDisabledTab(): void {
    const step = this.getCurrentStep();

    if (step) {
      this.alertService.open(step.disabledMessage, {
        status: TuiNotification.Warning,
        autoClose: 1000,
        hasCloseButton: false,
      }).subscribe();
    }
  }

  protected moveToStep(offset: number): void {
    const len = this.steps.length;
    if (len != 0) {
      this.currentStepIndex = (this.currentStepIndex + offset) % len;
      this.changeDetectorRef.detectChanges();
    }
  }

  protected getCurrentStepTemplate(): TemplateRef<any> | null {
    return this.getCurrentStep()?.ref ?? null;
  }
}
