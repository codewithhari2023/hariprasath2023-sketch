import { Component } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { NgForm } from '@angular/forms';
import { Register } from '../model/register';
import { AppResponse } from '../model/app-response';
import { Router } from '@angular/router';
import { AnimationOptions } from '@angular/animations';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  options: AnimationOptions = {
    path: '/assets/auth.json',
  };
  constructor(private authservice:AuthService,private route:Router){}
username='';
name='';
password='';
error=''
registers:Register[]=[]

  register(_register: NgForm) {
    let Register: Register = {
      username:this.username,
      name:this.name,
      password: this.password,
    };
  
    
    this.authservice.register(Register).subscribe({
      next:(response:AppResponse)=>{
        this.registers.push(response.data);
        
      },
      error: (err) => {
       this.error=err;
       console.log(this.error);
       
      },
      complete: () => console.log('There are no more actions happening.'),
      
    });
    this.route.navigate(["login"])
    }

}
