import { NgModule } from '@angular/core';
import { FormsModule, NgModelGroup, ReactiveFormsModule } from '@angular/forms';
import { ExceluploadsComponent } from './exceluploads/exceluploads.component';
import { IssuebookComponent } from './issuebook/issuebook.component';
import { LeaveRequestComponent } from './leave-request/leave-request.component';
import { MailTriggerComponent } from './mail-trigger/mail-trigger.component';
import { NotifyComponent } from './notify/notify.component';
import { OrderdetailsComponent } from './orderdetails/orderdetails.component';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from '../app.component';
import { BarcodeScannerComponent } from './barcode-scanner/barcode-scanner.component';
import { BorrowdetailsComponent } from './borrowdetails/borrowdetails.component';
import { UpdatebookComponent } from './updatebook/updatebook.component';
import { RouterModule } from '@angular/router';
import { DataUserRouterModule } from './data-user.routing.module';
import { CommonModule } from '@angular/common';
import { DataUserComponent } from './data-user.component';

@NgModule({
  declarations: [
    DataUserComponent,
    ExceluploadsComponent,
    IssuebookComponent,
    LeaveRequestComponent,
    MailTriggerComponent,
    NotifyComponent,
    OrderdetailsComponent,
    BarcodeScannerComponent,
    BorrowdetailsComponent,
    UpdatebookComponent

  ],
  imports:[FormsModule,RouterModule,DataUserRouterModule,ReactiveFormsModule,CommonModule]
})
export class DataUSerModule {}
