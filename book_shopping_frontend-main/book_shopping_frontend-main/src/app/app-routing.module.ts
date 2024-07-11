import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { RegisterComponent } from './component/register/register.component';
import { HomeComponent } from './component/home/home.component';
import { AdminHomeComponent } from './component/admin/home/home.component';
import { authGuard } from './guard/auth.guard';
import { AddCategoriesComponent } from './component/admin/add-categories/addcategories.component';
import { AddUserDetailsComponent } from './component/admin/add-user-details/add-user-details.component';
import { AddBookComponent } from './component/admin/add-book/add-book.component';
import { AddOrdersComponent } from './component/admin/add-orders/add-orders.component';
import { CartComponent } from './component/cart/cart.component';
import { OrderComponent } from './component/order/order.component';



const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent},
  { path: 'admin', component:AdminHomeComponent},
  {path:'addCategories',component:AddCategoriesComponent},
  {path:'addUserDetails',component:AddUserDetailsComponent},
  {path:'AddBook',component:AddBookComponent},
  {path:'AddOrder',component:AddOrdersComponent},
  {path:'cart',component:CartComponent},
  {path:'order',component:OrderComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
