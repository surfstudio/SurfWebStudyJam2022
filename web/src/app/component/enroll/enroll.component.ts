import { Component, isDevMode  } from '@angular/core';
import { ActivatedRoute} from '@angular/router';

import { EventService } from 'service/event.service';
import { EventInfo } from 'entity/event-info';


@Component({
  selector: 'app-enroll',
  templateUrl: './enroll.component.html',
  styleUrls: ['./enroll.component.less'],
})
export class EnrollComponent {
  protected state: 'loading' | 'welcome' | 'applyment' | 'success' = 'loading';

  protected event?: EventInfo;

  protected readonly devMode = isDevMode();

  constructor(private eventService: EventService, private activateRoute: ActivatedRoute) {
    const eventId = activateRoute.snapshot.params['eventId'];
    this.eventService.getEventInfo(eventId).subscribe(result => {
      this.event = result;
      this.state = 'welcome'
    }, error => {
      console.log(error);
      if (this.devMode) {
        this.state = 'welcome'
      }
    });
  }
}
