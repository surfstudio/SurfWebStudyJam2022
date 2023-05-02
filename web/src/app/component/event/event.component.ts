import { Component, Inject, TemplateRef, ViewChild } from '@angular/core';
import { ActivatedRoute} from '@angular/router';

import { EventService } from 'service/event.service';
import { EventInfo } from 'entity/event-info';

import { TestingService } from 'service/testing.service';


import { CandidateService } from 'service/candidate.service';
import { Candidate } from 'entity/candidate';


@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.less']
})
export class EventComponent {
  protected event?: EventInfo | null;

  protected candidates: Candidate[] = [];

  protected scores?: any | null;

  constructor(
			  private eventService: EventService,
              private candidateService: CandidateService,
              private testingService: TestingService,
              private activateRoute: ActivatedRoute) {
    const eventId = activateRoute.snapshot.params['eventId'];
    this.eventService.getEventInfo(eventId).subscribe(result => {
      this.event = result;
    }, error => {
      console.log(error);
    });
    this.candidateService.getAllByEventId(eventId).subscribe(result => {
      this.candidates = result;
      this.testingService.getAllScores(eventId).subscribe(result => {
            this.scores = result;
          }, error => {
            console.log(error);
          });
    }, error => {
      console.log(error);
    });
  }

  getCandidateScore(index: number) {
    const score = this.scores?.scores[(index + 1) % this.scores?.scores.length]?.score;
    return score ? Math.round(100*score) : -1;
  }

}
