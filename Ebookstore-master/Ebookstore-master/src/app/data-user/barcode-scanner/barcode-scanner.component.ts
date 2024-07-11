import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AppResponse } from 'src/app/model/app-response';
import { Barcode } from 'src/app/model/barcode';
import { Book } from 'src/app/model/book';
import { Cart } from 'src/app/model/cart';
import { CommonUser } from 'src/app/model/common-user';
import { BarcodeService } from 'src/app/service/barcode.service';
import { BookService } from 'src/app/service/book.service';
import { BorrowService } from 'src/app/service/borrow.service';
import { CartService } from 'src/app/service/cart.service';

import { StorageService } from 'src/app/service/storage.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-barcode-scanner',
  templateUrl: './barcode-scanner.component.html',
  styleUrls: ['./barcode-scanner.component.css']
})
export class BarcodeScannerComponent implements OnInit {
  error="";
bookName: any;
author: any;
barcode:Barcode[]=[]
book:Book[]=[]
count=1;
carts:Cart[]=[]
constructor(private bookservice:BookService,private storageservice:StorageService,private cartservice:CartService,private userservice:UserService,private borrowservice:BorrowService){}
  ngOnInit(): void {
    this.bookservice.GetBooks().subscribe({
      next: (response: AppResponse) => {
        if(response&& response.data)
        {
        this.book=response.data
         console.log(response.data);
        }else{
          console.log("response is null");
          
        }
      },
    });
  }
  AddtoCart(book:Book){
  
    let user = this.storageservice.getLoggedInUser();
    const Cart: Cart = {
      id: 0,
      userId: user.id,
      bookId: book.id,
      book: book,
      count: this.count,
    };
    // console.log(Cart.bookId,"cart bookId");
  
    let alreadyexists = Cart.bookId;
    if (alreadyexists) {
      this.count++;
    }
  
  
    this.cartservice.postCart(Cart).subscribe({
      next: (response: AppResponse) => {
        this.carts.push(response.data);
        console.log(this.carts);
      },
      error: (err) => {
        let message: string = err?.error?.error?.message;
        this.error = message.includes(',') ? message.split(',')[0] : message;
      },
    });
  }
 
  requestBook(id: number) {
    let user: CommonUser = this.storageservice.getLoggedInUser();
    let rBook = {
      userId: user.id,
      bookId: id,
    };
  
    this.borrowservice.RequestBook(rBook).subscribe({
      next: (Response) => {
        console.log("success");
      },
    });
  }

}
