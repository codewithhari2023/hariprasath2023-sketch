import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/appResponse';
import { urlEndpoint } from '../utils/constant';
import { Book } from '../model/book';
import { NgForm } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private http:HttpClient) { }
  getBook():Observable<AppResponse>{
    return this.http.get<AppResponse>(`${urlEndpoint.baseUrl}/admin/book/all`)
  }
  postBook(book:FormData){
    return this.http.post<AppResponse>(`http://localhost:8080/api/admin/book`,book)
  }
  deleteBook(id: number): Observable<AppResponse> {
    return this.http.delete<AppResponse>(
      `http://localhost:8080/api/admin/book/${id}`
    );
  }
  UpdateBook(book:Book):Observable<AppResponse>{
    return this.http.put<AppResponse>(`${urlEndpoint.baseUrl}/admin/book`,book)
  }
  getcategory():Observable<AppResponse>{
    return this.http.get<AppResponse>(``)
  }
}
