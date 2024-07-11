import { Component } from '@angular/core';
import { AnimationOptions } from 'ngx-lottie';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  options: AnimationOptions = {
    path: '/assets/auth.json',
  };
name: any;
username: any;
password: any;
constructor(private authservice:AuthService){}
}
