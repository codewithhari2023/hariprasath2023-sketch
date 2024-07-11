import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Email } from '../model/email';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/app-response';
import { urlEndpoint } from 'src/Util/Constant';
import { NgForm } from '@angular/forms';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class EmailService {

  constructor(private http:HttpClient,private storage:StorageService) { }
  senderemail=this.storage.getLoggedInUser().username;
  mailtrigger(email:Email):Observable<AppResponse>{
    return this.http.post<AppResponse>(`http://localhost:8080/api/email`,email)
  }
}
