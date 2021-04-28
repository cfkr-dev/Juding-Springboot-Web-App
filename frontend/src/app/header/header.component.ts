import {Component, Input, OnInit} from '@angular/core';
import {LoggedInUserService} from '../logged-in-user.service';
import {UserInterface} from '../user/user.interface';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['../../assets/vendor/font-awesome/css/all.css', '../../assets/css/header.css']
})
export class HeaderComponent implements OnInit {

  currentUser: UserInterface;

  // 0 -> Default (logged-in user)
  // 1 -> Index page
  // 2 -> Only show "return to index" button
  @Input() mainPage: number;

  headerType2 = [
    '/login',
    '/termsAndConditionsOfUse',
    '/cookiePolicy'
  ];

  constructor(private loggedUser: LoggedInUserService, public router: Router) {
    if (this.mainPage === undefined) {
      this.mainPage = 0;
    }
    if (this.headerType2.includes(this.router.url)){
      this.mainPage = 2;
    }
  }

  ngOnInit(): void {
    this.loggedUser.getLoggedUser().subscribe(
      (currentUser => this.currentUser = currentUser)
    );
  }
}
