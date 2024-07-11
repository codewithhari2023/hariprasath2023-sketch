import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, ObservedValueOf, tap } from 'rxjs';
import { AppResponse } from '../model/app-response';
import { Barcode } from '../model/barcode';
import { NgForm } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class BarcodeService {
  private apiUrl = 'http://localhost:8080/api/barcode/generate';

  constructor(private Http:HttpClient) { }
  getBarcode(id:number):Observable<AppResponse>{
    return this.Http.get<AppResponse>(`http://localhost:8080/api/barcode/${id}/image`)
  }
  postBarcode(barcode:any):Observable<AppResponse>{
    return this.Http.post<AppResponse>(`http://localhost:8080/api/barcode/generate`,barcode)
  }
  generateBarcode(bookData: any): Observable<any> {
    return this.Http.post(this.apiUrl, bookData, { responseType: 'blob' });
  }
}
