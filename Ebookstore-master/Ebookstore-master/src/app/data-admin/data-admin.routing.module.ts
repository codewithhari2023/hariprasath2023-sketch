import { RouterModule, Routes } from "@angular/router";
import { DataAdminComponent } from "./data-admin.component";
import { authGuard } from "../guard/auth.guard";
import { BookDetailsComponent } from "./book-details/book-details.component";
import { UploadExcelComponent } from "./upload-excel/upload-excel.component";
import { UserDetailComponent } from "./user-detail/user-detail.component";
import { NgModule } from "@angular/core";

const routes:Routes=[{path:'',component:DataAdminComponent,canActivate:[authGuard]},
    { path: 'book-details', component: BookDetailsComponent },
    { path: 'user-detail', component: UserDetailComponent },
    { path: 'Excel', component: UploadExcelComponent },
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
  })
  export class DataADminRouterModule{}