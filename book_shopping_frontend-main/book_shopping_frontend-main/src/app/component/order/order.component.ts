import { Component, OnInit } from '@angular/core';
import { Address } from 'src/app/model/address';
import { AppResponse } from 'src/app/model/appResponse';
import { AppUser } from 'src/app/model/appUser';
import { Cart } from 'src/app/model/cart';
import { Order } from 'src/app/model/order';
import { OrdersService } from 'src/app/service/orders.service';
import { StorageService } from 'src/app/service/storage.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css'],
})
export class OrderComponent implements OnInit  {
  userId: number = 0;
  bookId: number = 0;
  statusId: number = 0;
  error: string = '';
  currentorder:Order|undefined;
  carts: Cart[] = [];
  orderStatus:string="";
  book: string = '';
  emptycart:Boolean=true
  userOrder:Order[]=[];
  constructor(
    private orderservice: OrdersService,
    private storageservice: StorageService,
    private userservice:UserService
  ) {}
  ngOnInit(): void {
    this.getuserOrder();
  }
  getuserOrder(){
      this.orderservice.getUserOrder().subscribe({
        next: (response: any) => {
          this.userOrder = response.data;
        }
    });
  }
  loadUserDetails(){
    const useid=this.storageservice.getLoggedInUser().id;
    this.userservice.getUser().subscribe(
      (respone:AppResponse)=>{
        if(respone&&respone.data&&Array.isArray(respone.data)){
        const useradress=respone.data.find(user=>user && user.addressList.length>0)
        if(useradress){
          const firstaddress=useradress.addressList[0].id;
          const loguser=this.storageservice.getLoggedInUser();
          if(loguser){
            const order={
             userId:loguser.id,
             addressId:firstaddress,
             orderStatus:this.orderStatus,
             id:0,
             statusID:this.statusId
            };
            this.orderservice.postOrder(order).subscribe({
              next: (response: AppResponse) => {
                this.currentorder=response.data
                this.carts=[];
                this.emptycart=true

              },
              error: (err) => {
                let message: string = err?.error?.error?.message;
                this.error = message.includes(',') ? message.split(',')[0] : message;
              },
            });
          }
        }       
      }
      }
    )
   }
   checkout(){
    const loguser=this.storageservice.getLoggedInUser();
    if(loguser){
      this.loadUserDetails();
    }
   }

}
