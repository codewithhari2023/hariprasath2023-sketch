import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { urlEndpoint } from 'src/Util/Constant';
import { AppResponse } from '../model/app-response';
import { Observable } from 'rxjs';
import { Address } from '../model/address';
import { StorageService } from './storage.service';
import { CommonUser } from '../model/common-user';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl: string = 'http://localhost:8080/api/user';

  constructor(private Http:HttpClient,private storageservice:StorageService) { }
  getUser():Observable<AppResponse>{
    return this.Http.get<AppResponse>(`${urlEndpoint.baseUrl}/admin/user/all`)

  }
  getaddress():Observable<AppResponse>{;
    let id:Number=this.storageservice.getLoggedInUser().id;
    return this.Http.get<AppResponse>(`http://localhost:8080/api/address/${id}`)
  }
  postaddress(address:Address):Observable<AppResponse>{
    return this.Http.post<AppResponse>(`${urlEndpoint.baseUrl}/address`,address)
  }
  deleteadress(id:number):Observable<AppResponse>{
     return this.Http.delete<AppResponse>(`${urlEndpoint.baseUrl}/address/${id}`)
  }
  getUseraddress():Observable<AppResponse>{
    const user=this.storageservice.getLoggedInUser().id;
    
    return this.Http.get<AppResponse>(`http://localhost:8080/api/address/`+user)
  }
  getAllUsersExceptCurrentUser(): Observable<AppResponse> {
    return this.Http.get<AppResponse>(
  (`${this.baseUrl}/except/ `+this.storageservice.getLoggedInUser().id)
    );
  }

  // Retrieve the conversation ID between two users
  getConversationIdByUser1IdAndUser2Id(
    user1Id: number,
    user2Id: number
  ): Observable<AppResponse> {
    user1Id=this.storageservice.getLoggedInUser().id
    
    return this.Http.get<AppResponse>(this.baseUrl.concat(`/conversation/${user1Id}/${user2Id}`), {
      params: { user1Id: user1Id, user2Id: user2Id },
    });
  }

  // Retrieve the currently logged-in user from local storage
  currentUser(): CommonUser {
    return JSON.parse(localStorage.getItem('user') || '{}');
  }
}
