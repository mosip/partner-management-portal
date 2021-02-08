import { ErrorComponent } from './error/error.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotFoundComponent } from './not-found/not-found.component';
import { MaterialModule } from './material.module';
import { MatPaginatorIntl } from '@angular/material';
import { I18nModule } from '../i18n.module';
import { HamburgerComponent } from '../shared/hamburger-menu/hamburger-menu.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { StatusPipe } from './pipes/status.pipe';
import { DateFormatPipe } from './pipes/date-format.pipe';
import { DialogComponent } from './dialog/dialog.component';

@NgModule({
  imports: [CommonModule, MaterialModule, I18nModule , ReactiveFormsModule, FormsModule],
  declarations: [
    NotFoundComponent,
    HamburgerComponent,
    ErrorComponent,
    StatusPipe,
    DateFormatPipe,
    DialogComponent
  ],
  exports: [
    I18nModule,
    NotFoundComponent,
    MaterialModule,
    HamburgerComponent,
    ErrorComponent,
    StatusPipe,
    DateFormatPipe,
    DialogComponent
  ],
  entryComponents: [DialogComponent],
  providers: [
    {
      provide: MatPaginatorIntl
    }
  ]
})
export class SharedModule {}
