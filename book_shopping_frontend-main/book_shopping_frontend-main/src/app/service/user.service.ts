import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/appResponse';
import { urlEndpoint } from '../utils/constant';
import { Address } from '../model/address';
import { AppUser } from '../model/appUser';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private Http :HttpClient,private storageservice:StorageService) { }
  getUser():Observable<AppResponse>{
    return this.Http.get<AppResponse>(`${urlEndpoint.baseUrl}/admin/user/all`)

  }
  getaddress():Observable<AppResponse>{
    let user:AppUser=this.storageservice.getLoggedInUser();
    return this.Http.get<AppResponse>(`${urlEndpoint.baseUrl}/user`+user.id)
  }
  postaddress(address:Address):Observable<AppResponse>{
    return this.Http.post<AppResponse>(`${urlEndpoint.baseUrl}/user/address`,address)
  }
}
  