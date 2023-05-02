import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';

import { of, catchError } from 'rxjs';
import { tap } from 'rxjs/operators';

import { environment } from '../../environments/environment'

import { TestInfo } from 'entity/test-info'
import { Answer } from 'entity/answer'


@Injectable({
  providedIn: 'root'
})
export class TestingService {
  private url: string;

  constructor(private http: HttpClient) {
    this.url = `${environment.SURFWEBSTUDYJAM_API_URL}testing/test_variant/`;
  }

  public startTest(testVariantId: string): Observable<TestInfo> {
    return this.http.post<TestInfo>(`${this.url}start/${testVariantId}`, null);
  }

  public getTestInfo(testVariantId: string): Observable<TestInfo> {
    return this.http.get<TestInfo>(`${this.url}info/${testVariantId}`).pipe(tap(this.appendAnswerValues));;
  }

  public submitCurrentAnswerAndMoveNext(answer: Answer): Observable<TestInfo> {
    return this.http.post<TestInfo>(`${this.url}`, answer).pipe(tap(this.appendAnswerValues));
  }

  private appendAnswerValues(testInfo: TestInfo): void {
    testInfo.currentQuestion?.answers.forEach(answer => answer.value = false);
  }

  public getCandidateScore(candidateId: string): Observable<any> {
    return this.http.get<any>(`${this.url}score/${candidateId}`);
    /*.pipe(
      tap((x: any) => {x.score || 0}),
      catchError(err => of(0))
    );*/
  }

  public getAllScores(eventId: string): Observable<any> {
    return this.http.get<any>(`${this.url}scores/${eventId}`);
  }
}
