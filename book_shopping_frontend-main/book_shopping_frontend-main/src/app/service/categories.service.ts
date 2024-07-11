import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/appResponse';
import { urlEndpoint } from '../utils/constant';
import { Category } from '../model/category';

@Injectable({
  providedIn: 'root',
})
export class CategoriesService {
  constructor(private http: HttpClient) {}
  getCategories(): Observable<AppResponse> {
    return this.http.get<AppResponse>(
      `${urlEndpoint.baseUrl}/admin/category/all`
    );
  }
  postCategory(category: Category): Observable<AppResponse> {
    return this.http.post<AppResponse>(
      `${urlEndpoint.baseUrl}/admin/category`,
      category
    );
   }
   deleteCategory(id: number): Observable<AppResponse> {
    return this.http.delete<AppResponse>(
      `${urlEndpoint.baseUrl}/admin/category/${id}`
    );
  }
  Updatecategory(category:Category):Observable<AppResponse>{
    return this.http.put<AppResponse>(`${urlEndpoint.baseUrl}/admin/category`,category)
  }

}
