import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';

import { EventInfo } from 'entity/event-info'


@Injectable({
  providedIn: 'root'
})
export class EventService {
  private url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8080/api/events/';
  }

  public getEventInfo(eventId: string): Observable<EventInfo> {
    return this.http.get<EventInfo>(`${this.url}${eventId}`)
  }
}
