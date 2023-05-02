import { Component, OnInit, Inject, Input, HostBinding } from '@angular/core';

import { CandidateService } from 'service/candidate.service';
import { Candidate } from 'entity/candidate';

import { TestingService } from 'service/testing.service';


import {DomSanitizer} from '@angular/platform-browser';
import {TUI_IS_MOBILE} from '@taiga-ui/cdk';
import {TuiPdfViewerOptions, TuiPdfViewerService} from '@taiga-ui/kit';
import {PolymorpheusContent} from '@tinkoff/ng-polymorpheus';

import { Observable } from 'rxjs';

@Component({
  selector: 'app-candidate-info',
  templateUrl: './candidate-info.component.html',
  styleUrls: ['./candidate-info.component.less']
})
export class CandidateInfoComponent implements OnInit {

  protected activeItemIndex = 0;

  pass = false;

  @Input()
  @HostBinding('attr.candidate')
  candidate!: Candidate;

  protected candidateTestingScoreInfo$ = new Observable<number>();

  constructor(
  		@Inject(DomSanitizer) private readonly sanitizer: DomSanitizer,
      @Inject(TuiPdfViewerService) private readonly pdfService: TuiPdfViewerService,
      @Inject(TUI_IS_MOBILE) private readonly isMobile: boolean,
      @Inject(TestingService) private testingService: TestingService,
  ) {

  }

  score: number = -1

  ngOnInit(): void {
    //this.candidateTestingScoreInfo$ = this.testingService.getCandidateScore(this.candidate.id)
    this.testingService.getCandidateScore(this.candidate.id).subscribe(result => {
      this.score = Math.round(result.score * 100)
    }, error => {
      console.log(error)
      this.score = 0
    })
  }

  show(): void {
       const pdf = `http://192.168.0.100:8081/external-files/files/${(this.candidate as any).cvFileId}`

       this.pdfService
            .open(
                this.sanitizer.bypassSecurityTrustResourceUrl(
                    this.isMobile
                        ? `https://drive.google.com/viewerng/viewer?embedded=true&url=${pdf}`
                        : pdf,
                )
            )
            .subscribe();
  }

}
