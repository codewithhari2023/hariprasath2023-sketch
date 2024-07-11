import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, timer, switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private checkInterval = 50000  ; // 5 minutes
  private baseUrl = 'http://localhost:8080/api';
  constructor(private http: HttpClient, private router: Router) {
    this.startSessionCheck();
  }

  checkSessionStatus() {
    return this.http.get(`${this.baseUrl}/session/status  `, { responseType: 'text' }); 
  }

  startSessionCheck(): void { 
    timer(0, this.checkInterval).pipe(
      switchMap(() => this.checkSessionStatus())
    ).subscribe(status => {
      if (status === 'Session expired') {
        this.router.navigate(['/']);
      }
    });
  }
  getSessionStatus(): Observable<string> {
    return this.http.get<string>(`${this.baseUrl}/session/status`);
  }

  logout(): Observable<any> {
    return this.http.get(`${this.baseUrl}/session /logout`);
  }
}
