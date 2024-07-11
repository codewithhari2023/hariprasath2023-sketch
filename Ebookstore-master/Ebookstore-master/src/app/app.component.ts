import { AnimationOptions } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Cart } from './model/cart';
import { LoaderService } from './service/loader.service';
import { CartService } from './service/cart.service';
import { AuthService } from './service/auth.service';
import { RoleService } from './service/role.service';
import { StorageService } from './service/storage.service';
import { CommonUser } from './model/common-user';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
 styleUrls:['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'Ebookstore';
  cart:Cart[]=[]
  options:AnimationOptions = {
    path: '/assets/Load.json',
    delay:1000
  };
 
  isAdmin: boolean = false;
  isLoggedIn: boolean = false;
  isDatauser:boolean=false
  isDataAdmin:boolean=false
  isUser:boolean=false
 user:any
  constructor(
    private authService: AuthService,
    public loaderService: LoaderService,
    private cartservice:CartService,
    private storage:StorageService
  ) {}

  ngOnInit(): void {
    this.authService.isAdmin$.subscribe((isAdmin) => {
      this.isAdmin = isAdmin;
    });
    this.authService.isdatauser$.subscribe((isdatauser) => {
      this.isDatauser =isdatauser ;
    });
    this.authService.isdataAdmin$.subscribe((isdataAdmin) => {
      this.isDataAdmin= isdataAdmin;
    });
    this.authService.isUser$.subscribe((isUser) => {
      this.isUser= isUser;
    });

    this.authService.isLoggedIn$.subscribe((isLoggedIn) => {
      this.isLoggedIn = isLoggedIn;
    });

   
    
  }

  logout(): void {
    this.authService.logout();
  }
}
