import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';

import { Candidate } from 'entity/candidate'


@Injectable({
  providedIn: 'root'
})
export class CandidateService {
  private url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://192.168.0.100:8088/core/candidates/';
  }

  public createCandidate(candidate: Candidate): Observable<Candidate> {
    return this.http.post<Candidate>(this.url, candidate);
  }
}
