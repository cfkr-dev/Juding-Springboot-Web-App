import {Component} from '@angular/core';
import {LoginService} from './login.service';
import {Router} from '@angular/router';

interface LoginForm {
  nickname: string;
  password: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../../assets/vendor/bootstrap/v4/css/bootstrap.css', '../../assets/vendor/font-awesome/css/all.css', '../../assets/css/style.css', '../../assets/css/header.css', '../../assets/css/bootstrapAccomodations.css', '../../assets/css/loginAndRegistration.css'],
  providers: [LoginService]
})
export class LoginComponent {
  public loginForm: LoginForm;
  public passwordType: string;
  public error: number;

  constructor(private loginService: LoginService, private router: Router) {
    this.loginForm = {nickname: '', password: ''};
    this.passwordType = 'password';
    this.error = 0;
    if (this.router.url === '/logout') {
      this.loginService.logout().subscribe(
        (response) => this.error = 3
      );
    }
  }

  changeVisibility(): void {
    if (this.passwordType === 'text') {
      this.passwordType = 'password';
    } else if (this.passwordType === 'password') {
      this.passwordType = 'text';
    }
  }

  formLogin(event: Event): void {
    event.preventDefault();
    this.loginService.login(this.loginForm.nickname, this.loginForm.password).subscribe(
      (successful) => this.router.navigate(['/myHome']),
      (error) => {
        if (error.status === 403) {
          this.error = 1;
        } else {
          this.error = 2;
        }
      }
    );
  }
}
