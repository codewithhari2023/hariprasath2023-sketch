import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/app-response';
import { Excel } from '../model/excel';
import * as e from 'cors';
import { NgForm } from '@angular/forms';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
@Injectable({
  providedIn: 'root'
})
export class ExcelService {

  private apiUrl = 'http://your-api-url/download';

  constructor(private http: HttpClient) {}

  downloadExcel(): Observable<HttpResponse<Blob>> {
    return this.http.get(this.apiUrl, {
      responseType: 'blob',
      observe: 'response',
    });
  }
  getBookExcel():Observable<AppResponse>{
    let Excel:Excel[]=[]
    return this.http.post<AppResponse>(`http://localhost:8080/api/excel/downlods`,Excel);
  }
  getUSerExcel(id:number):Observable<AppResponse>{
    return this.http.get<AppResponse>(`http://localhost:8080/api/excel/download/user/${id}`)
  }
  PostBookExcel(excel:FormData):Observable<AppResponse>{
    return this.http.post<AppResponse>(`http://localhost:8080/api/book/download`,excel)
  }
  postUSerExcel(excel:FormData):Observable<AppResponse>{
    return this.http.post<AppResponse>(`http://localhost:8080/api/excel/upload`,excel)
  }
  exportToExcel(data: any[], fileName: string, sheetName: string): void {
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, sheetName);
    const excelBuffer: any = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
    this.saveAsExcelFile(excelBuffer, fileName);
  }

  private saveAsExcelFile(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    saveAs(data, fileName + '.xlsx');
  }


}
