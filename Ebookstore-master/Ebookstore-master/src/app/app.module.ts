import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { CartComponent } from './cart/cart.component';
import { ProfileComponent } from './profile/profile.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { OrderComponent } from './order/order.component';
import { LoadinterceptorService } from './service/InterCeptor/loadinterceptor.service';
import { AuthInterceptorService } from './service/InterCeptor/authinterceptor.service';
import { RegisterComponent } from './register/register.component';
import { AdminComponent } from './admin/admin.component';
import { BorrowComponent } from './borrow/borrow.component';

import { RolesComponent } from './admin/roles/roles.component';
import { TermsAndConditoinsComponent } from './terms-and-conditoins/terms-and-conditoins.component';
import { ReturnBookComponent } from './return-book/return-book.component';
import { UserHistoryComponent } from './user-history/user-history.component';
import { HistoryComponent } from './history/history.component';
import { DatePipe } from '@angular/common';
import { NgxBarcode6Module } from 'ngx-barcode6';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


import player from 'lottie-web';
import { NgxPaginationModule } from 'ngx-pagination';
import { RxStompService, rxStompServiceFactory } from '@stomp/ng2-stompjs';
import { myRxStompConfig } from './Config/my-rx-stomp.config';
import { WebsocketService } from './service/websocket.service';

import { LottieModule } from 'ngx-lottie';
import { DataUserRouterModule } from './data-user/data-user.routing.module';
import { DataADminRouterModule } from './data-admin/data-admin.routing.module';
import { ChatComponent } from './chat/chat.component';
import { UsernameComponent } from './username/username.component';
import { BetterDatePipe } from './better-date.pipe';
import { StompService } from './service/stomp.service';
export function playerFactory() {
  return player;
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    CartComponent,
    ProfileComponent,
    OrderComponent,
    RegisterComponent,
    AdminComponent,
    BorrowComponent,
    RolesComponent,
    TermsAndConditoinsComponent,
    ReturnBookComponent,
    UserHistoryComponent,
    HistoryComponent,
    ChatComponent,
    UsernameComponent,
    BetterDatePipe

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    NgxBarcode6Module,
    DataUserRouterModule,
    DataADminRouterModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-right',
      preventDuplicates: true,
    }),   
    BrowserAnimationsModule,
    BrowserAnimationsModule,
    NgxPaginationModule,
    ToastrModule.forRoot(),
    LottieModule.forRoot({player:playerFactory})
    

    
  ],
  providers: [
    DatePipe, {
      provide: HTTP_INTERCEPTORS,
      useClass: LoadinterceptorService,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true,
    },
    WebsocketService,
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory,
      deps: [myRxStompConfig]
    },
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory,
      deps: []
    },
    StompService
  
    
  ],

  bootstrap: [AppComponent]
})
export class AppModule { }
