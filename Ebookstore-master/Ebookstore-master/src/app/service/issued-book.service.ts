import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/app-response';
import { IssueBook } from '../model/issue-book';

@Injectable({
  providedIn: 'root'
})
export class IssuedBookService {

  constructor(private http: HttpClient) { }

  getIssuebook(): Observable<AppResponse> {
   
        return this.http.get<AppResponse>(`http://localhost:8080/api/admin/history/all`);
      
  }
  issueBook(IssueBook:IssueBook):Observable<AppResponse>{
    return this.http.post<AppResponse>("http://localhost:8080/api/admin/history/issue-book ",IssueBook);
  }
}
