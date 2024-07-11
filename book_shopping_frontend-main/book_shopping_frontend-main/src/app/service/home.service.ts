import { HttpClient } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable, map } from 'rxjs';
import { urlEndpoint } from '../utils/constant';
import { AppResponse } from '../model/appResponse';
import { Book } from '../model/book';

@Injectable({
  providedIn: 'root',
})
export class HomeService {

  constructor(private http: HttpClient) {}
  getAllBooks(): Observable<AppResponse> {
    return this.http.get<AppResponse>(`${urlEndpoint.baseUrl}book/all`)

   
  }
  
}
