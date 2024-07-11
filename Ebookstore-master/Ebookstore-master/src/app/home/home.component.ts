import { Component, OnInit } from '@angular/core';
import { CartService } from '../service/cart.service';
import { StorageService } from '../service/storage.service';
import { FormBuilder } from '@angular/forms';
import { CategoryService } from '../service/category.service';
import { Subject } from 'rxjs';
import { Book } from '../model/book';
import { Cart } from '../model/cart';
import { AppResponse } from '../model/app-response';
import { BookService } from '../service/book.service';
import { Category } from '../model/category';
import { Address } from '../model/address';
import { UserService } from '../service/user.service';
import { BorrowService } from '../service/borrow.service';
import { CommonUser } from '../model/common-user';
import { Chat } from '../model/chat';
import { SessionService } from '../service/session.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  

  book:Book[]=[]
  carts:Cart[]=[]
  address:Address[]=[]
  error="";
  count=1;
  currentorder: any;
  currentPage:number=1;
  itemsPerPage=5;
  emptycart: boolean=true;
  user:number=this.storageservice.getLoggedInUser().id;
  paginatedBooks: Book[]=this.book;
  constructor(private bookservice:BookService,private storageservice:StorageService,private cartservice:CartService,private userservice:UserService,private borrowservice:BorrowService,private sessionService:SessionService,private toastr:ToastrService){}
  ngOnInit(): void {
    this.bookservice.GetBooks().subscribe({
      next: (response: AppResponse) => {
        if(response&& response.data)
        {
        this.book=response.data
         console.log(response.data);
         console.log(this.book.length);
         
        }else{
          console.log("response is null");

          
        }
      },
    });
    this.sessionService.startSessionCheck()
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
logout() {
this.storageservice.removeLoggedInUser()
this.storageservice.removeAuthData()
this.sessionService.logout().subscribe(
  () => {
    this.toastr.success('Logged out successfully.');
  },
  (error) => {
    this.toastr.error('Error logging out.');
  }
);
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
messages: Chat[] = [];
  newMessage: Chat = {id:0, sender: '', message: '', timestamp: new Date() };


  // sendMessage() {
  //   this.chatService.sendMessage(this.newMessage).subscribe(response => {
  //     console.log(response); // Handle the response as needed
  //     this.newMessage = { id:0,sender: '', message: '', timestamp: new Date() }; // Clear the input fields
  //   });
  // }

  // getChatHistory(userId: number) {
  //   this.chatService.getChatHistory(userId).subscribe(history => {
  //     this.messages = history;
  //   });
  // }
  username: string = '';
  password: string = '';

  paginateBooks() {
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = Math.min(startIndex + this.itemsPerPage, this.book.length);
    this.paginatedBooks = this.book.slice(startIndex, endIndex);
  }

  setPage(pageNumber: number) {
    this.currentPage = pageNumber;
    console.log('Current Page:', this.currentPage);
    this.paginateBooks();
  }
  

  totalPages(): number {
    return Math.ceil(this.book.length / this.itemsPerPage);
  }

  getPageNumbers(): number[] {
    return Array.from({ length: this.totalPages() }, (_, i) => i + 1);
  }
  checkSessionStatus(): void {
    this.sessionService.getSessionStatus().subscribe(
      (response: string) => {
        this.toastr.success(response);
      },
      (error) => {
        if (error.status === 401) {
          this.toastr.error('Session expired. Please log in again.');
        } else {
          this.toastr.error('Error checking session status.');
        }
      }
    );
  }



 
}
  

