import { Injectable } from '@angular/core';
import { AppResponse } from '../model/appResponse';
import { urlEndpoint } from '../utils/constant';
import { HttpClient } from '@angular/common/http';
import { Category } from '../model/category';
import { Observable } from 'rxjs';
import { AppUser } from '../model/appUser';
import { StorageService } from './storage.service';
import { Cart } from '../model/cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {
error:String="";
cart:Cart[]=[]
  constructor(private http:HttpClient,private storageservice:StorageService) { }
    getUserCart(): Observable<AppResponse> {
    let user:AppUser=this.storageservice.getLoggedInUser();
   return this.http.get<AppResponse>(`http://localhost:8080/api/cart/`+user.id)
  }
  postCart(cart:Cart):Observable<AppResponse>{
    return this.http.post<AppResponse>(`${urlEndpoint.baseUrl}/cart`,cart)
  }
  DeleteCart(id:number,bookid:number):Observable<AppResponse>{
    let user:AppUser=this.storageservice.getLoggedInUser();
    return this.http.delete<AppResponse>(`${urlEndpoint.baseUrl}/cart/`+user.id+`/${bookid}`)
  }
  getcountofcart(){
    return this.cart.length;
  }
}
