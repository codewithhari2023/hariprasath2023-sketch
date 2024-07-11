import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppResponse } from '../model/app-response';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';
import { Card } from '../model/card';

@Injectable({
  providedIn: 'root'
})
export class CardService {

  constructor(private Http :HttpClient,private Storage:StorageService) { }
  GetUserCard():Observable<AppResponse>{
  const user=this.Storage.getLoggedInUser().id;
    return this.Http.get<AppResponse>(`http://localhost:8080/api/card/usercard/`+user);
 }
 AddCard(card:Card):Observable<AppResponse>{
  return this.Http.post<AppResponse>(`http://localhost:8080/api/card/add`,card)
 }
}
