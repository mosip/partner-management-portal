import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ResourcesRoutingModule } from './resources-routing.module';
import { MaterialModule } from 'src/app/shared/material.module';
import { I18nModule } from 'src/app/i18n.module';
import { ListViewComponent } from './list-view/list-view.component';
import { SingleViewComponent } from './single-view/single-view.component';
import { SubListViewComponent } from './sub-list-view/sub-list-view.component';
import { SubSingleViewComponent } from './sub-single-view/sub-single-view.component';
import { CertUploadComponent } from './cert-upload/cert-upload.component';
import { MasterDataCommonHeaderComponent } from './shared/master-data-common-header/master-data-common-header.component';
import { MasterDataCommonBodyComponent } from './shared/master-data-common-body/master-data-common-body.component';
import { MasterDataCommonViewComponent } from './shared/master-data-common-view/master-data-common-view.component';
import { TableComponent } from './shared/table/table.component';
import { ToolbarComponent } from './shared/toolbar/toolbar.component';
import { DialogComponent } from './shared/dialog/dialog.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    ResourcesRoutingModule,
    MaterialModule,
    I18nModule, 
    FormsModule, 
    ReactiveFormsModule
  ],
  declarations: [
    MasterDataCommonHeaderComponent,
    MasterDataCommonBodyComponent,
    MasterDataCommonViewComponent,
    ListViewComponent,
    SingleViewComponent,
    SubListViewComponent,
    SubSingleViewComponent,
    TableComponent,
    ToolbarComponent,
    DialogComponent,
    CertUploadComponent
  ],
  exports:[TableComponent, ToolbarComponent, DialogComponent],
  entryComponents: [DialogComponent]
})
export class ResourcesModule { }
