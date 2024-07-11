import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/app-response';
import { Category } from '../model/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private Http:HttpClient) { }
  getCategory():Observable<AppResponse>{
    return this.Http.get<AppResponse>(`http://localhost:8080/api/category/all`)
  }
  postCategory(category:Category):Observable<AppResponse>{
    return this.Http.post<AppResponse>(`http://localhost:8080/api/category`,category)
  }
}
