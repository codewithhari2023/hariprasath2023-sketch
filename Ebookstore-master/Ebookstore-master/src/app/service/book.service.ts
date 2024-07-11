import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { AppResponse } from '../model/app-response';
import { Book } from '../model/book';
import { urlEndpoint } from 'src/Util/Constant';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  private apiUrl = 'http://localhost:8080/api/book';

  constructor(private Http:HttpClient) { }
  DeleteBook(id:number):Observable<AppResponse>{
    return this.Http.delete<AppResponse>(`${urlEndpoint.baseUrl}/book/${id}`)}
  
  GetBooks():Observable<AppResponse>{
  return this.Http.get<AppResponse>(`${urlEndpoint.baseUrl}/book/all`)
  }
  updateBook(bookId: number, formData: FormData): Observable<Book> {
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    
    return this.Http.put<Book>(`${this.apiUrl}/${bookId}`, formData, { headers }).pipe(
      catchError(error => {
        return throwError(error);
      })
    );
  }
  AddBook(book:FormData):Observable<AppResponse>{
    return this.Http.post<AppResponse>(`${urlEndpoint.baseUrl}/book`,book)
  }
  
}
