import { Component, OnInit } from '@angular/core';
import { AppResponse } from 'src/app/model/appResponse';
import { Order } from 'src/app/model/order';
import { Orderstatus } from 'src/app/model/orderstatus';
import { OrdersService } from 'src/app/service/orders.service';

@Component({
  selector: 'app-add-orders',
  templateUrl: './add-orders.component.html',
  styleUrls: ['./add-orders.component.css']
})
export class AddOrdersComponent implements OnInit{
  constructor (private orderservice:OrdersService){}
  error:string="";
  OrderDetails:Order[]=[]
  orderstatuses:Orderstatus[]=[]
  orderId:number=0;
  statusId:number=0;
  ngOnInit(): void {
    this.orderservice.getOrder().subscribe({
      next:(response:any)=>{
        this.OrderDetails=response.data
   
      },
      error:(err)=>{
        let message:string=err?.error?.error?.message;
        this.error=message.includes(",")?message.split(",")[0]:message;
        },
    });
  }
  getOrderstatus(){
    this.orderservice.getAllstatus().subscribe({
      next:(respone:AppResponse)=>{
        this.orderstatuses=respone.data
      }
    })
  }
  updateOrder(OrderDetails:Order){
    const orderstauts:{[key:string]:number}={
      'Pending':1,
      'confirmed':2,
      'OutforDelievey':3,
      'delievered':4

    }
    const Orderstatus=OrderDetails.orderStatus
    // const  statusId=orderstauts[Orderstatus]
    const Orderstatusinfo:Orderstatus={
            orderId:OrderDetails.id,
            statusId:OrderDetails.statusID
    }
    this.orderservice.updateorder(Orderstatusinfo).subscribe({
      next:(response)=>{
        if(response.error){
          console.log("invalid Api call");
          
        }

      }
    })
  }
  editOrderstatus(Order:Order){
    this.updateOrder(Order)

  }
}