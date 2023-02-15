import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment'

import { EventInfo } from 'entity/event-info'


@Injectable({
  providedIn: 'root'
})
export class EventService {
  private url: string;

  constructor(private http: HttpClient) {
    this.url = `${environment.SURFWEBSTUDYJAM_API_URL}core/events/`;
  }

  public getEventInfo(eventId: string): Observable<EventInfo> {
    return this.http.get<EventInfo>(`${this.url}${eventId}`)
  }
}
