import { Component } from '@angular/core';
import { Order } from '../model/order';
import { Cart } from '../model/cart';
import { Address } from '../model/address';
import { CartService } from '../service/cart.service';
import { StorageService } from '../service/storage.service';
import { UserService } from '../service/user.service';
import { AppResponse } from '../model/app-response';
import { NgForm } from '@angular/forms';
import { OrderService } from '../service/order.service';
import { Book } from '../model/book';
import { BookService } from '../service/book.service';
import { Card } from '../model/card';
import { CardService } from '../service/card.service';
import { BorrowService } from '../service/borrow.service';
import { Borrow } from '../model/borrow';
import {MatIconModule} from '@angular/material/icon';
import { ToastrService } from 'ngx-toastr';
import { AnimationOptions } from 'ngx-lottie';
@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
 
})
export class CartComponent {
  emptycarts:AnimationOptions = {
    path: '/assets/emptycart.json',
  };
    userId = this.storageservice.getLoggedInUser().id;
    bookId: number = 0;
    error: string = '';
    currentorder: Order | undefined;
    currentBorrow: Borrow |undefined;
    carts: Cart[] = [];
    book:Book={id:0,title:'',description:'',price:0,author:'',categoryId:0}
    orderStatus: string = '';
    emptycart: Boolean = true;
    Orders: Order[] = [];
    address: Address[] = [];
    selectedAddress: any;
    countSub: any;
    cartCount: number = 0;
  expdate: any;
  cvv: any;
  balance: any;
  cardNumber: any;
  Card:Card={id:0,cvv:0,balance:0,expirydate:0,cardNumber:0,userId:this.storageservice.getLoggedInUser().id}
  constructor(
      private cartservice: CartService,
      private storageservice: StorageService,
      private orderservice: OrderService,   
      private userservice: UserService,
      private bookservice:BookService,
      private cardservice:CardService,
      private borrowservice:BorrowService,
      private toastr:ToastrService
    ) {
    }
    ngOnInit(): void {
      this.cartservice.getUserCart().subscribe({
        next: (response: any) => {
          this.carts=response.data
          console.log(this.carts);
        },
        error: (err) => {
          let message: string = err?.error?.error?.message;
          this.error = message.includes(',') ? message.split(',')[0] : message;
        },
      });
      this.cardservice.GetUserCard().subscribe({
        next: (response: any) => {
          this.Card=response.data
          console.log(this.Card);
        },
        error: (err) => {
          let message: string = err?.error?.error?.message;
          this.error = message.includes(',') ? message.split(',')[0] : message;
        },
      });
      
    
      
    }

    loadUserDetails() {
      const userid = this.storageservice.getLoggedInUser().id;
      
      this.userservice.getUseraddress().subscribe((respone: AppResponse) => {
        this.Card.balance=this.Card.balance-this.book.price
        this.address=respone.data.address   
          
            for (let f of this.address) {
              const address = f.id;
              const firstaddress = address;
              const order = {
                userId: userid,
                addressId: firstaddress,
                orderStatus: this.orderStatus,
                id: 0,
                statusId: 0,
              };
              this.orderservice.postOrder(order).subscribe({
                next: (response: AppResponse) => {
                this.balance=this.balance-this.book.price;
                  this.carts = [];
                  this.emptycart = true;
                  console.log("adddress");
                  
                },
                error: (err) => {
                  let message: string = err?.error?.error?.message;
                  this.error = message.includes(',')
                    ? message.split(',')[0]
                    : message;
                },
              });
            }
          
        }
      );
    }
    checkout() {
      if (this.carts.length === 0) {
        this.toastr.error('Your cart is empty', 'Error');
        return;
      }
      
      const loguser = this.storageservice.getLoggedInUser();
      console.log(loguser);
      
      if (loguser) {
        this.loadUserDetails();
      }
    }

    getCartCount(id: number): number {
      let count: number = this.carts.find((cart) => cart.id === id)?.count ?? 0;
      return count;
    }
    Deletecart(id: number, bookid: number) {
      this.cartservice.DeleteCart(id, bookid).subscribe({
        next: (response) => {
          this.carts = this.carts.filter(
            (cart) => cart.id !== id && cart.book?.id !== bookid
          );
          localStorage.removeItem(JSON.stringify(this.carts));
            },
        error: (err: { error: { error: { message: string; }; }; }) => {
          let message: string = err.error?.error?.message;
          this.error = message.includes(',') ? message.split(',')[0] : message;
        },
      });
      this.toastr.success('Item removed from cart', 'Success'); 
    }
    resetform(myform: NgForm) {
      myform.reset();
    }
    makecard() {
      let userId:number=this.storageservice.getLoggedInUser().id;
      let Card:Card={
        id:0,
        cvv:this.cvv,
        cardNumber:this.cardNumber,
        balance:this.balance,
        expirydate:this.expdate,
        userId:userId
      }
      this.cardservice.AddCard(Card).subscribe({ next: (response: any) => {
        this.Card=response.data
        console.log(this.Card);
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },})
      
      }
    
  logout(){
    this.storageservice.removeLoggedInUser()
  }
  incrementQuantity(cartItem: Cart) {
    cartItem.count++;
  }

  decrementQuantity(cartItem: Cart) {
    if (cartItem.count > 1) {
      cartItem.count--;
    } else {
      this.toastr.warning('Minimum quantity reached', 'Warning');
    }
  }

  calculateTotal(cartItem: Cart): number {
    return cartItem.count * cartItem.book.price;
  }

}
