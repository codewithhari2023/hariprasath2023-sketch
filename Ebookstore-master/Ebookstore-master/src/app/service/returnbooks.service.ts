import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/app-response';

@Injectable({
  providedIn: 'root'
})
export class ReturnbooksService {

  constructor(private http:HttpClient,private storageservice:StorageService) { }
  returnedBook(): Observable<AppResponse> {
    return this.http.get<AppResponse>(
      'http://localhost:8080/api/admin/returnedBook'
    );
  }
  returnBook(id: number,amt:number|null) {

    const url = `http://localhost:8080/api/history/${id}`;
    return this.http.put<AppResponse>(url, null);
  }
}
