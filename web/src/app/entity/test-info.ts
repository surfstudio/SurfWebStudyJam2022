export class TestInfo {
  testVariantId: string = '';
  maxAcceptableDurationSec: number = 0;
  finishingAt: Date | null = null;
  currentQuestion: CurrentQuestion | null = null;
  state: 'NOT_STARTED' | 'STARTED' | 'FINISHED' = 'NOT_STARTED';
}

export class CurrentQuestion {
  title: string = '';
  questionType: 'SINGLE_CHOICE' | 'MULTIPLE_CHOICE' = 'SINGLE_CHOICE';
  answers: Array<QuestionInfo> = new Array<QuestionInfo>();
}

export class QuestionInfo {
  answerId: string = '';
  title: string = '';
  value: boolean = false;
}
