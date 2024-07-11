import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/app-response';
import { CommonUser } from '../model/common-user';

@Injectable({
  providedIn: 'root'
})
export class UserhistoryService {

  constructor(private http:HttpClient,private storageservice:StorageService) { }
  getUsersHistory(): Observable<AppResponse> {
   
    return this.http.get<AppResponse>("http://localhost:8080/api/admin/history/all");
  
}
getUserHistory(): Observable<AppResponse> {
  let user:CommonUser=this.storageservice.getLoggedInUser();
  return this.http.get<AppResponse>(
    "http://localhost:8080/api/history/"+user.id
  );
}  
}
