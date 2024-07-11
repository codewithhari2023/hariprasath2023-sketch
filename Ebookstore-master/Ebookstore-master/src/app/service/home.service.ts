import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/app-response';
import { urlEndpoint } from 'src/Util/Constant';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HomeService {
constructor(private http:HttpClient){}
  getAllBooks(): Observable<AppResponse> {
    return this.http.get<AppResponse>(`${urlEndpoint.baseUrl}book/all`)
  }
 
}
