import { HttpClient, HttpXsrfTokenExtractor } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { Observable } from 'rxjs';
import { User } from './types';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient, private location: Location, private httpXsrfTokenExtractor: HttpXsrfTokenExtractor) { }

  authenticate(): Observable<User> {
    return this.httpClient.get<User>('/user');
  }

  login(): void {
    window.open('/oauth2/authorization/keycloak', '_self');
  }

  logout(): Observable<any> {
    var formData: any = new FormData();
    formData.append("_csrf", this.httpXsrfTokenExtractor.getToken());
    return this.httpClient.post<any>('/logout', formData);
  }
}
