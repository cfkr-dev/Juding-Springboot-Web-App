import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {UserInterface} from './user/user.interface';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoggedInUserService {

  constructor(private http: HttpClient) {
  }

  getLoggedUser(): Observable<UserInterface> {
    return this.http.get('/api/users/me', {withCredentials: true}) as Observable<UserInterface>;
  }

  getLoggedUserImage(role: string, licenseId: string): Observable<Blob>{
    return this.http.get('/api/' + role + '/' + licenseId + '/image', {responseType: 'blob'}) as Observable<Blob>;
  }
}
