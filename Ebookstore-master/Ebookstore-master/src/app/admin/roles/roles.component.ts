import { Component, OnInit } from '@angular/core';
import { AppResponse } from 'src/app/model/app-response';
import { CommonUser } from 'src/app/model/common-user';
import { Role } from 'src/app/model/role';
import { RoleService } from 'src/app/service/role.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.css']
})
export class RolesComponent implements OnInit {
  users:CommonUser[]=[]
  Roles:Role[]=[]
  role=''
  username=''
  name=''
  password=''
  id=0
  constructor(private userservice:UserService,private roleservice:RoleService){}
  ngOnInit(): void {
    this.roleservice.GetRole().subscribe({
      next: (response: AppResponse) => {
        if(response&& response.data)
        {
        this.Roles=response.data
         console.log(response.data);
        }else{
          console.log("response is null");
          
        }
      },
    });
    this.userservice.getUser().subscribe({
      next: (response: AppResponse) => {
        if(response&& response.data)
        {
        this.users=response.data
         console.log(response.data);
        }else{
          console.log("response is null");
          
        }
      },
    });
  }
  createRole(){
   let Role:Role={id:this.id,role:this.role,username:this.username,name:this.name,password:this.password}
   this.roleservice.AddRole(Role).subscribe({
    next: (response: AppResponse) => {
      if(response&& response.data)
      {
      this.Roles=response.data
       console.log(response.data);
      }else{
        console.log("response is null");
        
      }
    },
  });
   
  }
  

}
