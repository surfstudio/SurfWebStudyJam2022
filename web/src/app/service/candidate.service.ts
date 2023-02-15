import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';

import { environment } from '../../environments/environment'

import { Candidate } from 'entity/candidate'


@Injectable({
  providedIn: 'root'
})
export class CandidateService {
  private url: string;

  constructor(private http: HttpClient) {
    this.url = `${environment.SURFWEBSTUDYJAM_API_URL}core/candidates/`;
  }

  public createCandidate(candidate: Candidate): Observable<Candidate> {
    return this.http.post<Candidate>(this.url, candidate);
  }
}
