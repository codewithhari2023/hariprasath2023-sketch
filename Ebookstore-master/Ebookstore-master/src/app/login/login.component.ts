import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { Router } from '@angular/router';
import { Form, NgForm } from '@angular/forms';
import { Login } from '../model/login';
import { AppResponse } from '../model/app-response';
import { CommonUser } from '../model/common-user';
import { Register } from '../model/register';
import { StorageService } from '../service/storage.service';
import { AnimationOptions } from '@angular/animations';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent  {
  Login:AnimationOptions = {
    path: '/assets/Loginpage.json',
  };
  username = '';
  password = '';
  error: string = '';
  loginSuccess: boolean = false;
  loginError: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private storageservice: StorageService
  ) {}

login(_loginForm: NgForm) {
    // Introduce a 4-second delay
 

    let login: Login = {
      username: this.username,
      password: this.password,
    };

    console.log(login);

    this.authService.login(login).subscribe({
      next: (response: AppResponse) => {
        let user: CommonUser = response.data;
        this.authService.setLoggedIn(user);
        this.router.navigate(['/home']); // Navigate to home or any other route upon success
      },
      error: (err) => {
       this.error=err;
       console.log(this.error);
       
      },
      complete: () => console.log('There are no more actions happening.'),
    });

    if (this.username.length >= 6 && this.password.length >= 3) {
      this.loginSuccess = true;
      this.loginError = false;
    } else {
      this.loginSuccess = false;
      this.loginError = true;
    }
  }
}

// Sleep function
function sleep(ms: number): Promise<void> {
  return new Promise(resolve => setTimeout(resolve, ms));
}
   

  


  