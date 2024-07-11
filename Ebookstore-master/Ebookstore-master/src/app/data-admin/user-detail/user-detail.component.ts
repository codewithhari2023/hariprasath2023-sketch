import { Component, OnInit } from '@angular/core';
import { AppResponse } from 'src/app/model/app-response';
import { CommonUser } from 'src/app/model/common-user';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {
  commonuser:CommonUser[]=[]
  constructor(private userservice:UserService){}
  ngOnInit(): void {
    this.userservice.getUser().subscribe({ next: (response: AppResponse) => {
      if(response&& response.data)
      {
      this.commonuser=response.data
       console.log(response.data);
      }else{
        console.log("response is null");
        
      }
    },
      
     })
  }


}
