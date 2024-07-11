import { Injectable } from '@angular/core';
import { Register } from '../model/register';
import { AppResponse } from '../model/app-response';
import { Observable } from 'rxjs/internal/Observable';
import { Router } from '@angular/router';
import { Login } from '../model/login';
import { BehaviorSubject, catchError, map, throwError } from 'rxjs';
import { StorageService } from './storage.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { CONSTANT, urlEndpoint } from 'src/Util/Constant';
import { CommonUser } from '../model/common-user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private router: Router,
    private http: HttpClient,
    private storageService: StorageService
  ) {
    if (storageService.getLoggedInUser().id != null) {
      this.setLoggedIn(storageService.getLoggedInUser());
    }
  }
  

  private isAdminSubject = new BehaviorSubject<boolean>(false);
  private isLoggedInSubject = new BehaviorSubject<boolean>(false);
  
  private isDataUser = new BehaviorSubject<boolean>(false); 
   private isDataAdmin = new BehaviorSubject<boolean>(false);
   private isUser = new BehaviorSubject<boolean>(false);
  isAdmin$: Observable<boolean> = this.isAdminSubject.asObservable();
  isLoggedIn$: Observable<boolean> = this.isLoggedInSubject.asObservable();
  isdatauser$: Observable<boolean> = this.isDataUser.asObservable();
  isdataAdmin$: Observable<boolean> = this.isDataAdmin.asObservable();
  isUser$: Observable<boolean> = this.isUser.asObservable();
  login(login: Login): Observable<AppResponse> {
    return this.http
      .post<AppResponse>(`${urlEndpoint.baseUrl}/auth/login`, login)
      .pipe(
        map((user: AppResponse) => {
          this.storageService.setAuthData(
            window.btoa(login.username + ':' + login.password)
          );
          console.log(user);

          return user;
        }),
        catchError(this.handleError)
      )
     
     
  }

  logout() {
    this.storageService.removeAuthData();
    this.isAdminSubject.next(false);
    this.isLoggedInSubject.next(false);
    this.storageService.removeLoggedInUser();
    this.storageService.removeRoute();
    this.router.navigate([''], { replaceUrl: true });
  }

  isAdmin(): boolean {
    return this.isAdminSubject.value;
  }

  isLoggedIn(): boolean {
    return this.isLoggedInSubject.value;
  }
  isDataUsers(): boolean {
    return this.isDataUser.value;
  }
  isDataAdmins(): boolean {
    return this.isDataAdmin.value;
  }
  isUsers(): boolean {
    return this.isUser.value;
  }


  setLoggedIn(user: CommonUser): void {
    this.storageService.setLoggedInUser(user);
    this.isLoggedInSubject.next(true);

    console.log(user.role.id);
    // this.router.navigate(['/'], { replaceUrl: true });
    if (user.role.role === 'PUBLIC') {
      this.isUser.next(true)
      this.router.navigate(['/home'], { replaceUrl: true });
    } else if (user.role.role === 'ADMIN') {
      this.isAdminSubject.next(true);
      this.router.navigate(['/admin'], { replaceUrl: true });
    } else if (user.role.role === 'librarian') {
      this.isDataUser.next(true)
      this.router.navigate(['/DataUser'], { replaceUrl: true });
    } else if (user.role.role === 'supervisor') {
      this.isDataAdmin.next(true)
      this.router.navigate(['/dataAdmin'], { replaceUrl: true });
    }
  }
  register(user: Register): Observable<AppResponse> {
    return this.http.post<AppResponse>(
      `${urlEndpoint.baseUrl}/auth/register`,
      user
    ).pipe(catchError(this.handleError));
  }
 
private handleError(errorRes: HttpErrorResponse) {
  let err = 'An unknown error occurred!';

  // Check if errorRes.error and errorRes.error.error exist
  if (!errorRes.error || !errorRes.error.error) {
    console.log("Error message:", err);
    return throwError(() => new Error(err));
  }

  // Check if the error message exists and handle specific error cases
  switch (errorRes.error.message) {
    case 'User already exists':
      err = 'This email exists already';
      break;
    case 'Email not found':
      err = 'This email does not exist.';
      break;
    case 'password not found':
      err = 'Incorrect password.';
      break;
    default:
      err = errorRes.error.error.message || err;
  }

  console.log("Error message:", err);
  return throwError(() => new Error(err));
}
}
