import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';

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

    return this.http.post<SFile>(`${this.url}resume`, formData)
  }
}
