import { Component } from '@angular/core';
import { AppResponse } from '../model/app-response';
import { Userhistory } from '../model/user-history';
import { UserhistoryService } from '../service/userhistory.service';

@Component({
  selector: 'app-user-history',
  templateUrl: './user-history.component.html',
  styleUrls: ['./user-history.component.css']
})
export class UserHistoryComponent {
  constructor(private userhistoryservice:UserhistoryService){}
  BookName:string=""
  StudentName:string=""
issuebooks:Userhistory[]=[]
Sno:number=1

ngOnInit(): void {
  this.userhistoryservice.getUsersHistory().subscribe({
    next: (response: AppResponse) => {
    console.log("jiiiii")
      if (response && response.data) {
        this.issuebooks = response.data;
        console.log(this.issuebooks)
      } else {
        console.error('Invalid API response format:', response);
     
      }
    },
    error: (err) => {
      console.log('An error occurred:', err);
    
    },
    complete: () => console.log('There are no more actions happening.'),
  });
}
}
