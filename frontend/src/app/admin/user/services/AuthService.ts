import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../models/user.model';

const BASE_URL = 'api';

@Injectable({providedIn: 'root'})
export class AuthService {

  logged: boolean;
  user: User;

  constructor(private http: HttpClient) {
    this.reqIsLogged();
  }

  reqIsLogged() {

    this.http.get('/api/users/me', { withCredentials: true }).subscribe(
      response => {
        this.user = response as User;
        this.logged = true;
      },
      error => {
        if (error.status != 404) {
          console.error('Error when asking if logged: ' + JSON.stringify(error));
        }
      }
    );

  }

  logIn(user: string, pass: string) {
    this.http.post(BASE_URL + '/login', { username: user, password: pass }, { withCredentials: true })
      .subscribe(
        (response) => alert('LoggedIn!'),
        (error) => alert('Wrong credentials')
      );
  }
}
