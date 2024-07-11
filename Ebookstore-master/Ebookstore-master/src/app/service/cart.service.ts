import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';
import { Cart } from '../model/cart';
import { Observable, ReplaySubject } from 'rxjs';
import { AppResponse } from '../model/app-response';
import { CommonUser } from '../model/common-user';
import { urlEndpoint } from 'src/Util/Constant';
import { Book } from '../model/book';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor(private http:HttpClient,private storageservice:StorageService) { }
  error:String="";
cart:Cart[]=[]
private cartCount = new ReplaySubject<number>(1);
cartCount$ = this.cartCount.asObservable();

    getUserCart(): Observable<AppResponse> {
    let user:CommonUser=this.storageservice.getLoggedInUser();
   return this.http.get<AppResponse>(`${urlEndpoint.baseUrl}/cart/`+user.id)
  }
  postCart(cart:Cart):Observable<AppResponse>{
    return this.http.post<AppResponse>(`${urlEndpoint.baseUrl}/cart/add`,cart)
  }
  DeleteCart(id:number,bookid:number):Observable<AppResponse>{
    let user:CommonUser=this.storageservice.getLoggedInUser();
    return this.http.delete<AppResponse>(`${urlEndpoint.baseUrl}/cart/${user.id}/${bookid}`)
  }
  getcountofcart(){
    return this.cart.length;
  }

  setCartCount(count: number) {
    localStorage.setItem("cart_count", JSON.stringify(count));
    this.cartCount.next(count);
  }
  AddtoCart(book: Book) { 

    
  
    let user: CommonUser = this.storageservice.getLoggedInUser();
    const Cart: Cart = {
      id: 0,
      userId: user.id,
      bookId: book.id,
      book: book,
      count: 1,
    };
  }
}
