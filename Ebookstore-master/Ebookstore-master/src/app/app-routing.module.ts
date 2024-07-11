import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { CartComponent } from './cart/cart.component';
import { OrderComponent } from './order/order.component';
import { authGuard } from './guard/auth.guard';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { AdminComponent } from './admin/admin.component';
import { DataAdminComponent } from './data-admin/data-admin.component';
import { DataUserComponent } from './data-user/data-user.component';
import { RolesComponent } from './admin/roles/roles.component';
import { HistoryComponent } from './history/history.component';
import { ReturnBookComponent } from './return-book/return-book.component';
import { UserHistoryComponent } from './user-history/user-history.component';
import { ChatComponent } from './chat/chat.component';
import { UsernameComponent } from './username/username.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  { path: 'cart', component: CartComponent },
  { path: 'order', component: OrderComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'dataAdmin', component: DataAdminComponent,loadChildren:()=>import('./data-admin/data-admin.module').then(m=>m.DataAdminModule) },
  { path: 'DataUser', component: DataUserComponent,loadChildren:()=>import('./data-user/data-user.module').then(m=>m.DataUSerModule) },
  { path: 'role', component: RolesComponent },
  { path: 'historybooks', component: HistoryComponent },
  { path: 'return', component: ReturnBookComponent },

  { path: 'user-history', component: UserHistoryComponent },
  { path: 'username', component: UsernameComponent },
  { path: 'chat', component: ChatComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
