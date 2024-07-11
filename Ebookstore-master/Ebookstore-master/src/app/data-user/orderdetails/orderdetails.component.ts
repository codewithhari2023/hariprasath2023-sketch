import { Component, OnInit } from '@angular/core';
import { AppResponse } from 'src/app/model/app-response';
import { Order } from 'src/app/model/order';
import { OrderService } from 'src/app/service/order.service';

@Component({
  selector: 'app-orderdetails',
  templateUrl: './orderdetails.component.html',
  styleUrls: ['./orderdetails.component.css']
})
export class OrderdetailsComponent implements OnInit{

  Orders:Order[]=[]
  book: any;
  constructor(private orderservice:OrderService){}
 ngOnInit(): void {

 }
 
 
}
