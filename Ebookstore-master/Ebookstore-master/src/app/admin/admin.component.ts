import { Component, inject, OnInit } from '@angular/core';
import { Role } from '../model/role';
import { RoleService } from '../service/role.service';
import { UserService } from '../service/user.service';
import { AppResponse } from '../model/app-response';
import { CommonUser } from '../model/common-user';
import { Email } from '../model/email';
import { EmailService } from '../service/email.service';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { map } from 'rxjs';
import { NgForm } from '@angular/forms';
import { StorageService } from '../service/storage.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent  implements OnInit{

message: any;
subject: any;
  constructor(private role:RoleService,private User:UserService,private emailservice:EmailService,private storageservice:StorageService){}
  private breakpointObserver = inject(BreakpointObserver);
  users:CommonUser[]=[];
  roles:Role[]=[];
  emails:Email[]=[]
  email:any
  ngOnInit(): void {
   
  }
  logout() {
this.storageservice.removeLoggedInUser()

  }


  /** Based on the screen size, switch from standard to one column per row */
 

 
  // mailtrigger(){
  //  let mail:Email={message:this.message,email:this.email,subject:this.subject}
  //  console.log(mail);
   
  //   this.emailservice.mailtrigger(mail).subscribe({ next: (response: AppResponse) => {
  //     if(response&& response.data)
  //     {
  //     this.roles=response.data
  //      console.log(response.data);
  //     }else{
  //       console.log("response is null");
        
  //     }
  //   },
      
  //    }) 
  // }
 


}
