import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/app-response';
import { CommonUser } from '../model/common-user';
import { StorageService } from './storage.service';
import { Borrow } from '../model/borrow';
import { RequestBook } from '../model/request-book';

@Injectable({
  providedIn: 'root'
})
export class BorrowService {
  private apiUrl = 'http://localhost:8080/api/penalty/amount';
  constructor(private http:HttpClient,private Storage:StorageService) { }
  getNotification(): Observable<AppResponse> {
    return this.http.get<AppResponse>(
      'http://localhost:8080/api/borrow/message'
    );
  }
  clear(id: number) {

    const url = `http://localhost:8080/api/borrow/${id}`;
    return this.http.put<AppResponse>(url, null);
  }
  RequestBook(requestBook:RequestBook): Observable<AppResponse>{
    return this.http.post<AppResponse>(
      `http://localhost:8080/api/borrow/send`,requestBook

    )

  }
  
  calculatePenalty(borrow: Borrow): Observable<AppResponse> {
    return this.http.post<AppResponse>(this.apiUrl, borrow);
  }

 
}
