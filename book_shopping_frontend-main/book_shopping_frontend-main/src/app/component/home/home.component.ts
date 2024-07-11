import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeService } from 'src/app/service/home.service';
import { Book } from 'src/app/model/book';
import { BookService } from 'src/app/service/book.service';
import { Category } from 'src/app/model/category';
import { CategoriesService } from 'src/app/service/categories.service';
import { CartService } from 'src/app/service/cart.service';
import { Cart } from 'src/app/model/cart';
import { AppResponse } from 'src/app/model/appResponse';
import { StorageService } from 'src/app/service/storage.service';
import { AppUser } from 'src/app/model/appUser';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls:['./home.component.css'],
})
export class HomeComponent {
  userId:number=0;
  error:string="";
  book:string="";
  bookId:number=0;
  carts:Cart[]=[]
    Books:Book[]=[];
    constructor(private bookservice:BookService,private cartservice:CartService,private storageservice:StorageService){}

  ngOnInit(): void {
    this.bookservice.getBook().subscribe({
      next:(Response:any)=>{
        this.Books=Response.data
        console.log(this.Books);
        
      },
      error:(err)=>{
        let message:string=err?.error?.error?.message;
        this.error=message.includes(",")?message.split(",")[0]:message;
      },
    });
  }
  AddtoCart(book:Book){
    let user:AppUser=this.storageservice.getLoggedInUser();
    const Cart:Cart={
      id:0,
      userId: user.id,
      bookId: book.id,
      book: book,
      count:1,
    };
      console.log(Cart);
      
      this.cartservice.postCart(Cart).subscribe({
        next:(response:AppResponse)=>{
          this.carts.push(response.data);
        },
            error: (err) => {
              let message: string = err?.error?.error?.message;
              this.error = message.includes(",")
                ? message.split(",")[0]
                : message;
            },
          });
      
    }
    
  getCartCount(bookId: number): number {
    let count: number = this.carts.find((cart) => cart.bookId === bookId)?.count ?? 0;
    return count;
  }

  
}
