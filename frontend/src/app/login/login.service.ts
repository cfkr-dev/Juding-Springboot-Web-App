import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';

@Injectable()
export class LoginService {

  constructor(private http: HttpClient, private router: Router) {
  }

  login(nickname: string, password: string): void {
    this.http.post('/api/login', {username: nickname, password}, {withCredentials: true})
      .subscribe(
        (response) => {
          this.http.get('/api/users/me', {withCredentials: true}).subscribe(
            (response2) => {
              console.log(response2);
              this.router.navigate(['/myHome']);
            },
            error => {
              if (error.status !== 404) {
                console.error('Error when asking if logged: ' + JSON.stringify(error));
                this.router.navigate(['/login']);
              }
            }
          );
        },
        (error) => alert('Wrong credentials')
      );
  }

  logout(): void{
    this.http.post('/logout', {}, {withCredentials: true}).subscribe(
      (response) => this.router.navigate([''])
    );
  }
}
