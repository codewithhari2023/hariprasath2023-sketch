import { Injectable } from '@angular/core';
import { LoaderService } from '../loader.service';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { finalize, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoadinterceptorService implements HttpInterceptor {
  constructor(private loaderService: LoaderService) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    this.loaderService.isLoading.next(true);
    return next.handle(req).pipe(
      finalize(async () => {
        // Sleep for 2 seconds
        await this.sleep(1000);
        this.loaderService.isLoading.next(false);
      })
    );
  }

  private sleep(ms: number): Promise<void> {
    return new Promise((resolve) => setTimeout(resolve, ms));
  }
}
