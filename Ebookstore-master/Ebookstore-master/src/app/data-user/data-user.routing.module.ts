import { RouterModule, Routes } from '@angular/router';
import { DataUserComponent } from './data-user.component';
import { BarcodeScannerComponent } from './barcode-scanner/barcode-scanner.component';
import { BorrowdetailsComponent } from './borrowdetails/borrowdetails.component';
import { ExceluploadsComponent } from './exceluploads/exceluploads.component';
import { LeaveRequestComponent } from './leave-request/leave-request.component';
import { MailTriggerComponent } from './mail-trigger/mail-trigger.component';
import { OrderdetailsComponent } from './orderdetails/orderdetails.component';
import { UpdatebookComponent } from './updatebook/updatebook.component';
import { IssuebookComponent } from './issuebook/issuebook.component';
import { NotifyComponent } from './notify/notify.component';
import { NgModule } from '@angular/core';

const routes: Routes = [
  {
    path: '',
    component: DataUserComponent,
  },

  { path: 'barcode', component: BarcodeScannerComponent },
  { path: 'exceluploads', component: ExceluploadsComponent },
  { path: 'leave', component: LeaveRequestComponent },
  { path: 'mail', component: MailTriggerComponent },
  { path: 'updatebook', component: UpdatebookComponent },
  { path: 'orderdetail', component: OrderdetailsComponent },
  { path: 'borrowdetail', component: BorrowdetailsComponent },
  { path: 'issue', component: IssuebookComponent },
  { path: 'notify', component: NotifyComponent },
];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DataUserRouterModule {}
