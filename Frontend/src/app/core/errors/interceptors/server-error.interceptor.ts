import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HttpErrorResponse,
} from '@angular/common/http';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Router } from '@angular/router';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = '';

        if (error.error instanceof ErrorEvent) {
          // Error del lado del cliente
          errorMessage = `Error: ${error.error.message}`;
        } else {
          // Error del lado del servidor
          errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
          if (error.status === 404) {
            this.router.navigate(['/not-found']);
          } else if (error.status === 500) {
            this.router.navigate(['/server-error']);
          }
          else if(error.status === 401){
            this.router.navigate(['/unatorized'])
          }
          else if (error.status === 403){
            this.router.navigate(['/unatorized'])
          }
        }

        // Puedes enviar el error a un servicio de logging si es necesario
        console.error(errorMessage);
        return throwError(() => new Error(errorMessage));
      })
    );
  }
}