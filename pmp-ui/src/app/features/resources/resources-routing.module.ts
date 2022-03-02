import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ListViewComponent } from './list-view/list-view.component';
import { SingleViewComponent } from './single-view/single-view.component';
import { CertUploadComponent } from './cert-upload/cert-upload.component';
import { SubListViewComponent } from './sub-list-view/sub-list-view.component';
import { SubSingleViewComponent } from './sub-single-view/sub-single-view.component';
import { RolesGuard } from 'src/app/core/services/roles.guard';

const routes: Routes = [
  { path: '', redirectTo: 'misp', pathMatch: 'full' },  
  { path: ':type/view', component: ListViewComponent, canActivate: [RolesGuard] },
  { path: ':type/create', component: SingleViewComponent, canActivate: [RolesGuard] },
  { path: ':type/single-view/:id', component: SingleViewComponent, canActivate: [RolesGuard] },
  { path: ':type/:childurl/view/:id', component: SubListViewComponent, canActivate: [RolesGuard] },
  { path: ':type/:childurl/view/:id/create', component: SubSingleViewComponent, canActivate: [RolesGuard] },
  { path: ':type/:childurl/view/:id/single-view/:childid', component: SubSingleViewComponent, canActivate: [RolesGuard] },
  { path: ':type/upload/:id', component: CertUploadComponent, canActivate: [RolesGuard] },
  { path: ':type/upload', component: CertUploadComponent, canActivate: [RolesGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ResourcesRoutingModule { }
