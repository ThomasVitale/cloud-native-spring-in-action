import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { tap } from 'rxjs/operators';

@Injectable()
export class HttpInterceptorImpl implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let finalRequest = this.prepareRequest(request);
    return next.handle(finalRequest).pipe(tap(() => {},
      (err: any) => {
        if (err instanceof HttpErrorResponse) {
          if (!request.url.endsWith('/user') && err.status === 401) {
            this.authService.login();
          } else if (request.url.endsWith('/user') && err.status === 401) {
            console.log("Unauthenticated session");
          }
        }
      }
    ));
  }

  prepareRequest(request: HttpRequest<any>): HttpRequest<any> {
    if (request.url.endsWith('post')) {
      return request.clone();
    }
    return request.clone({
      headers: request.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
  }
}
