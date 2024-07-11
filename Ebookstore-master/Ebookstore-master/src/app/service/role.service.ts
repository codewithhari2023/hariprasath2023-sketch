import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppResponse } from '../model/app-response';
import { Role } from '../model/role';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private Http :HttpClient) { }
  GetRole():Observable<AppResponse>{
    return this.Http.get<AppResponse>(`http://localhost:8080/api/admin/role/all`);
 }
 AddRole(Role:Role):Observable<AppResponse>{
  return this.Http.post<AppResponse>(`http://localhost:8080/api/admin/role/registerRole`,Role)
 }
}
