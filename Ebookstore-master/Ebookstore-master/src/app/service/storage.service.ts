import { Injectable } from '@angular/core';
import { CommonUser } from '../model/common-user';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }
  setLoggedInUser(user: CommonUser): void {
    localStorage.setItem("loggedInUser", JSON.stringify(user));
  }

  public getLoggedInUser(): CommonUser {
    return JSON.parse(localStorage.getItem("loggedInUser") || "{}");
  }

  public removeLoggedInUser(): void {
    localStorage.removeItem("loggedInUser");
  }

  public setRoute(route: string | null): void {
    if (route !== null) localStorage.setItem("route", route);
  }

  public getRoute(): string | null {
    return localStorage.getItem("route");
  }

  public removeRoute(): void {
    localStorage.removeItem("route");
  }

  setAuthData(authData: string) {
    localStorage.setItem("authData", authData);
  }

  public getAuthData(): string | null {
    return localStorage.getItem("authData");
  }
  
  public removeAuthData(): void {
    localStorage.removeItem("authData");
  }
}
