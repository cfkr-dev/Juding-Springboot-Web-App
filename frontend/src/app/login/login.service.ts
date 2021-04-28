import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable()
export class LoginService {

  constructor(private http: HttpClient) {
  }

  login(nickname: string, password: string): Observable<boolean> {
    return this.http.post('/api/login', {username: nickname, password}, {withCredentials: true}) as Observable<boolean>;
  }

  logout(): Observable<any> {
    return this.http.post('/api/logout', {}, {withCredentials: true}) as Observable<any>;
  }
}
