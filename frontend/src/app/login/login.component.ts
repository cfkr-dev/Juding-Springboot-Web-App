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
  styleUrls: ['../../assets/vendor/bootstrap/css/bootstrap.css', '../../assets/vendor/font-awesome/css/all.css', '../../assets/css/style.css', '../../assets/css/header.css', '../../assets/css/bootstrapAccomodations.css', '../../assets/css/loginAndRegistration.css'],
  providers: [LoginService]
})
export class LoginComponent {
  public loginForm: LoginForm;

  constructor(private loginService: LoginService, private router: Router) {
    this.loginForm = {nickname: '', password: ''};
    if (this.router.url === '/logout') {
      this.loginService.logout();
      this.router.navigate(['']);
    }
  }


  formLogin(event: Event): void {
    event.preventDefault();
    this.loginService.login(this.loginForm.nickname, this.loginForm.password);
  }
}
