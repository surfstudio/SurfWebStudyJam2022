import { Component, OnInit, Input, HostBinding, Output, EventEmitter, ViewChild, Inject } from '@angular/core';
import { FormControl, FormGroup, Validators, ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms'

import { Observable, of, Subject, timer } from 'rxjs';
import { map, switchMap, finalize } from 'rxjs/operators';

import { TuiAlertService, TuiNotification } from '@taiga-ui/core';
import { TuiFileLike } from '@taiga-ui/kit';

import {TUI_VALIDATION_ERRORS} from '@taiga-ui/kit';

import { StepFormComponent } from './step-form/step-form.component';

import { CandidateService } from 'service/candidate.service';
import { ResourceService } from 'service/resource.service';
import { Candidate } from 'entity/candidate';


const vcsUrls = /^(https?:\/\/)?(github|gitlab).com\/./

const telegramUrls = [
  /^(https?:\/\/)?t\.me\/(\w+?)(\/)?$/g,
  /^(https?:\/\/)?(\w+?)\.t\.me(\/)?$/g,
]
const telegramUsername = /^[A-Za-z][A-Za-z0-9_]{3,}[A-Za-z0-9]$/

@Component({
  selector: 'app-enroll-applyment',
  templateUrl: './enroll-applyment.component.html',
  styleUrls: ['./enroll-applyment.component.less'],
  providers: [
    {
      provide: TUI_VALIDATION_ERRORS,
      useValue: {
        required: 'Нужно заполнить это поле',
        email: 'Введите корректный email',
      }
    },
  ],
})
export class EnrollApplymentComponent implements OnInit {
  @Input()
  @HostBinding('attr.eventId')
  eventId!: string;

  @Output()
  protected onApply: EventEmitter<void> = new EventEmitter();

  @ViewChild(StepFormComponent)
  protected readonly stepForm?: StepFormComponent;

  protected readonly candidate = new Candidate();

  constructor(
    @Inject(TuiAlertService)
    private readonly alertService: TuiAlertService,

    @Inject(CandidateService)
    private candidateService: CandidateService,

    @Inject(ResourceService)
    private resourceService: ResourceService,
  ) { }

  ngOnInit() {
    this.candidate.eventId = this.eventId;
  }

  protected onInputFileReject(file: TuiFileLike | readonly TuiFileLike[]): void {
    this.alertService.open('Пожалуйста, прикрепите файл в формате pdf', {
       status: TuiNotification.Warning,
       autoClose: 1000,
       hasCloseButton: false,
     }).subscribe();
  }

  protected readonly rejectedFiles$ = new Subject<TuiFileLike | null>();
  protected readonly loadingFiles$ = new Subject<TuiFileLike | null>();

  private uploadFile(file: TuiFileLike): Observable<TuiFileLike | null> {
    this.loadingFiles$.next(file);
    const bFile = this.candidateForm['experience'].controls['cvControl'].value;
    return this.resourceService.uploadResume(bFile).pipe(
      map(() => {
        this.candidateForm['experience'].controls['cvUploadedControl'].setValue(file);
        return file;

        /*this.rejectedFiles$.next(file);
        return null;*/
      }),
      finalize(() => this.loadingFiles$.next(null)),
    );
  }


  protected removeFile(): void {
    this.candidateForm['experience'].controls['cvUploadedControl'].setValue(null);
    this.candidateForm['experience'].controls['cvControl'].setValue(null);
  }

  protected clearRejected(): void {
      this.removeFile();
      this.rejectedFiles$.next(null);
  }

  protected submit(): void {
    this.candidateService.createCandidate(this.candidate).subscribe(result => {
      this.onApply.emit();
    }, error => {
      console.log(error);
    });
  }

  private checkVcs: ValidatorFn = (field: AbstractControl):  ValidationErrors | null => {
    const value = field.value;
    return vcsUrls.test(value) ? null : {
      vcsError: 'Укажите ссылку на профиль в github или gitlab'
    }
  }

  private checkTelegram: ValidatorFn = (field: AbstractControl):  ValidationErrors | null => {
    const value = field.value;
    if (telegramUsername.test(value)) {
      return null;
    }
    const match = telegramUrls.
      map(r => [...value.matchAll(r)]).
      filter(m => m.length).
      map(m => m[0][2]);
    return match[0] && telegramUsername.test(match[0]) ? null :  {
      telegramError: 'Укажите действительный профиль Telegram'
    }
  }

  protected readonly candidateForm: { [key: string]: FormGroup } = {
    personal: new FormGroup({
      firstNameControl: new FormControl('', [
        Validators.required,
      ]),
      lastNameControl: new FormControl('', [
        Validators.required,
      ]),
    }),
    education: new FormGroup({
      universityControl: new FormControl('', [
        Validators.required,
      ]),
      facultyControl: new FormControl('', [
        Validators.required,
      ]),
      courseControl: new FormControl('', [
        Validators.required,
      ]),
    }),
    experience: new FormGroup({
      experienceControl: new FormControl('', [
        Validators.required,
      ]),
      vcsControl: new FormControl('', [
        Validators.required,
        this.checkVcs
      ]),
      cvControl: new FormControl('', [
        Validators.required,
      ]),
      cvUploadedControl: new FormControl('', [
        Validators.required,
      ]),
    }),
    contacts: new FormGroup({
      emailControl: new FormControl('', [
        Validators.required,
        Validators.email,
      ]),
      telegramControl: new FormControl('', [
        Validators.required,
    this.checkTelegram
      ]),
    }),
    feedback: new FormGroup({
      feedbackControl: new FormControl('', [
        Validators.required,
      ]),
    }),
  };

  protected readonly loadedFiles$ = this.
    candidateForm['experience'].
    controls['cvControl'].
    valueChanges.
    pipe(
      switchMap(file => (file ? this.uploadFile(file) : of(null))),
  );
}
