import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {EnrollComponent} from './component/enroll/enroll.component'
import {TestingComponent} from './component/testing/testing.component'
import {ProjectCardComponent} from "./component/card/project-card.component";

const routes: Routes = [
  { path: 'enroll/:eventId', component: EnrollComponent },
  { path: 'testing/:testVariantId', component: TestingComponent },
  { path: 'hello', component: ProjectCardComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
