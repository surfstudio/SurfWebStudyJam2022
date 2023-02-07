import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { EnrollComponent } from './component/enroll/enroll.component'

const routes: Routes = [
  { path: 'enroll/:eventId', component: EnrollComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
