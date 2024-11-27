import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpHeaders,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';

import { InterceptorStorage } from './interceptor-storage';

@Injectable()
export class ApiKeyInterceptor implements HttpInterceptor {
  constructor(private readonly interceptorStorage: InterceptorStorage) {}

  private createBaseHeaders() {
    return new HttpHeaders().set(
      'Api-Key',
      this.interceptorStorage.retrieveApiKey(),
    );
  }

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler,
  ): Observable<HttpEvent<unknown>> {
    return next.handle(
      request.clone({
        headers: this.createBaseHeaders(),
      }),
    );
  }
}
