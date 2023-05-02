import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {ProjectCard} from "../entity/project-card";

@Injectable({
  providedIn: 'root'
})
export class ProjectCardService {
  private readonly coreCardsPath: string = 'core/cards/'
  private readonly url: string;

  constructor(private http: HttpClient) {
    this.url = `${environment.SURFWEBSTUDYJAM_API_URL}${this.coreCardsPath}`;
  }

  public getProjectCardData(eventId: string): Observable<ProjectCard> {
    return this.http.get<ProjectCard>(`${this.url}${eventId}/card`)
  }

  public updateProjectCardData(eventId: string, updatedCard: ProjectCard): Observable<any> {
    return this.http.put(`${this.url}${eventId}`, updatedCard)
  }

}
