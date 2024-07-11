import { Component, OnInit } from '@angular/core';
import { AppResponse } from 'src/app/model/app-response';
import { BorrowService } from 'src/app/service/borrow.service';

@Component({
  selector: 'app-notify',
  templateUrl: './notify.component.html',
  styleUrls: ['./notify.component.css']
})
export class NotifyComponent implements OnInit {

  notifys:any[]=[]
  Sno:number=1;
  
  constructor(private notification:BorrowService){}
  ngOnInit(): void {
    this.notification.getNotification().subscribe({
      next:(resp:AppResponse)=>{
      this.notifys=resp.data
      console.log(this.notifys)
      }
    })
  }
    clear(id:number) {
     this.notification.clear(id).subscribe({
      next:(response)=>
      {
       this.ngOnInit()
        console.log(response)
  
      }
     })
      }
}
