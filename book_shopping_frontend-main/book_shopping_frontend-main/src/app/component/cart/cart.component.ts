import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { count } from 'rxjs';
import { AppResponse } from 'src/app/model/appResponse';
import { AppUser } from 'src/app/model/appUser';
import { Book } from 'src/app/model/book';
import { Cart } from 'src/app/model/cart';
import { Order } from 'src/app/model/order';
import { CartService } from 'src/app/service/cart.service';
import { OrdersService } from 'src/app/service/orders.service';
import { StorageService } from 'src/app/service/storage.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  userId: number = 0;
  bookId: number = 0;
  statusId: number = 0;
  error: string = '';
  currentorder:Order|undefined;
  carts: Cart[] = [];
  orderStatus:string="";
  book: string = '';
  emptycart:Boolean=true
  Orders: Order[] = [];
  constructor(
    private cartservice: CartService,
    private storageservice: StorageService,
    private orderservice: OrdersService,
    private userservice:UserService
  ) {}
  ngOnInit(): void {
    this.cartservice.getUserCart().subscribe({
      next: (response: any) => {
        this.carts = response.data;
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },
    });
  }
  AddtoCart(id: number) {
    let user: AppUser = this.storageservice.getLoggedInUser();
    const Cart: Cart = {
      id:0,
      userId: user.id,
      bookId: id,
      book: {
        id: 0,
        categoryId:0,
        title: '',
        description: '',
        author: '',
        price: 0,
        photo:""
      },
      count:0,
      
    };

    this.cartservice.postCart(Cart).subscribe({
      next: (response: AppResponse) => {
        this.carts.push(response.data);
        this.ngOnInit();
        this.getCartCount(id); 
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },
    })
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


  getCartCount(id:number):number{
    let count : number=this.carts.find((cart)=>cart.id===id)?.count??0;
    return count;
    
  } Deletecart(id: number,bookid:number) {
    this.cartservice.DeleteCart(id,bookid).subscribe({
      next: (response ) => {
        this.carts = this.carts.filter(
          (cart) => cart.id !== id  && cart.book.id!==bookid)
        this.ngOnInit();
      },
      error: (err) => {
        let message: string = err.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },
    });
  
}

}
