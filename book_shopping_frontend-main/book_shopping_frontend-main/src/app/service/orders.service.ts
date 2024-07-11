import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { urlEndpoint } from '../utils/constant';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/appResponse';
import { StorageService } from './storage.service';
import { AppUser } from '../model/appUser';
import { Order } from '../model/order';
import { Orderstatus } from '../model/orderstatus';

@Injectable({
  providedIn: 'root'
})
export class OrdersService {

  constructor(private http:HttpClient,private storageservice:StorageService) { }
  getOrder():Observable<AppResponse>{
    return this.http.get<AppResponse>(`${urlEndpoint.baseUrl}/admin/order/all`)
  }
  getUserOrder():Observable<AppResponse>{
    let user:AppUser=this.storageservice.getLoggedInUser();
    return this.http.get<AppResponse>(`http://localhost:8080/api/order/`+user.id)
  }
  postOrder(order:Order):Observable<AppResponse>{
    return this.http.post<AppResponse>(`${urlEndpoint.baseUrl}/order`,order)
  }
  getAllstatus():Observable<AppResponse>{
    return this.http.get<AppResponse>(`http://localhost:8080/api/admin/order/status/all`)
  }
  updateorder(orderstatus:Orderstatus):Observable<AppResponse>{
    return this.http.put<AppResponse>(`http://localhost:8080/api/admin/order/status`,orderstatus)
  }
}

