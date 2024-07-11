import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppResponse } from '../model/app-response';
import { Observable } from 'rxjs';
import { urlEndpoint } from 'src/Util/Constant';
import { Order } from '../model/order';
import { CommonUser } from '../model/common-user';
import { Status } from '../model/status';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
 
 

  constructor(private http:HttpClient,private storage:StorageService) { }

  getUserOrder():Observable<AppResponse>{
    let user:CommonUser=this.storage.getLoggedInUser();
    return this.http.get<AppResponse>(`http://localhost:8080/api/order/`+user.id)
  }
  postOrder(order:Order):Observable<AppResponse>{
    return this.http.post<AppResponse>(`${urlEndpoint.baseUrl}/order`,order)
  }
  getAllOrder():Observable<AppResponse>{
    return this.http.get<AppResponse>(`http://localhost:8080/api/admin/order/all`)
  }
  
}
