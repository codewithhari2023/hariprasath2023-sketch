import { Component, OnInit } from '@angular/core';
import { AppResponse } from 'src/app/model/appResponse';
import { UserDetails } from 'src/app/model/user-details';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-add-user-details',
  templateUrl: './add-user-details.component.html',
  styleUrls: ['./add-user-details.component.css']
})
export class AddUserDetailsComponent implements OnInit {
  constructor(private userservice:UserService){}
  error:string="";
 
    UserDetails:UserDetails[]=[]
    ngOnInit(): void {
      this.userservice.getUser().subscribe({
        next:(response:AppResponse)=>{
          this.UserDetails=response.data
        },
        error:(err)=>{
          let message:string=err?.error?.error?.message;
          this.error=message.includes(",")?message.split(",")[0]:message;
          },
      });
    }
   
}
