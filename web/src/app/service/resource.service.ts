import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';

import { TuiFileLike } from '@taiga-ui/kit';

import { SFile } from 'entity/s-file'


@Injectable({
  providedIn: 'root'
})
export class ResourceService {
  private url: string;

  constructor(private http: HttpClient) {
    this.url = 'http://localhost:8081/api/resource/';
  }

  public uploadResume(file: File): Observable<SFile> {
    const formData = new FormData();
    formData.append("resume", file);
    formData.append("reportProgress", "false");

    const httpOptions = {
      headers: new HttpHeaders({
      })
    };
    return this.http.post<SFile>(`${this.url}resume`, formData, httpOptions)
  }
}
