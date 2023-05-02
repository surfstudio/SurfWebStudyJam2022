import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {ProjectCard} from "../../entity/project-card";
import {Observable} from "rxjs";
import {ProjectCardService} from "../../service/project-card.service";
import {FormControl, FormGroup} from "@angular/forms";


@Component({
  selector: 'project-card',
  templateUrl: './project-card.component.html',
  styleUrls: ['./project-card.component.less'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProjectCardComponent implements OnInit {
  protected projectCard$: Observable<ProjectCard> | undefined;
  protected error: String = ''
  projectNote: string = ''
  version: number = 0

  constructor(public cardService: ProjectCardService) {
  }

  //Заглушка с id
  ngOnInit(): void {
    this.projectCard$ = this.cardService.getProjectCardData('a7a5a364-49b4-454f-9906-3468964ad132')
    this.projectCard$.subscribe(result => {
        this.projectNote = result.projectNote
        this.version = result.version
      },
      error => {
        console.log(`ERROR ${error.message}`)
        this.error = error.message
      }
    )
  }

  myForm: FormGroup = new FormGroup({
    "projectNote": new FormControl(``,),
  });

  updateCard() {
    let updatedCard = new ProjectCard();
    updatedCard.projectNote = this.projectNote
    updatedCard.version = this.version
    this.cardService.updateProjectCardData('a7a5a364-49b4-454f-9906-3468964ad132', updatedCard).subscribe(result => console.log(result))
    this.projectCard$ = this.cardService.getProjectCardData('a7a5a364-49b4-454f-9906-3468964ad132')
  }

}
