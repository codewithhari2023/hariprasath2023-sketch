import { NgModule } from '@angular/core';
import { DataAdminComponent } from './data-admin.component';
import { BookDetailsComponent } from './book-details/book-details.component';
import { UploadExcelComponent } from './upload-excel/upload-excel.component';
import { UserDetailComponent } from './user-detail/user-detail.component';
import { CommonModule } from '@angular/common';
import { DataADminRouterModule } from './data-admin.routing.module';

@NgModule({
  declarations: [
    BookDetailsComponent,
    UploadExcelComponent,
    UserDetailComponent,
  ],
  imports:[CommonModule,DataADminRouterModule]
})

export class DataAdminModule {}
