import {Component, Input} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {LoggedInUserService} from '../logged-in-user.service';

export interface PageInfoInterface {
  headerValue: number;
  isAdmin?: boolean;
  loading: boolean;
}

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['../../assets/vendor/font-awesome/css/all.css', '../../assets/css/header.css']
})
export class HeaderComponent {
  // headerValue possible values:
  // 0 -> Default (logged-in user)
  // 1 -> Index page
  // 2 -> Only show "return to index" button
  pageInfo: PageInfoInterface;

  @Input() isType1: boolean;

  headerType2 = [
    '/login',
    '/logout',
    '/403',
    '/404',
    '/500',
    '/termsAndConditionsOfUse',
    '/cookiePolicy'
  ];

  constructor(public router: Router, public loggedInUser: LoggedInUserService) {
    this.pageInfo = {headerValue: 0, isAdmin: false, loading: true};
    this.headerChange();
    router.events.subscribe(
      (change) => {
        if (change instanceof NavigationEnd) {
          this.headerChange();
        }
      }
    );
  }

  headerChange(): void {
    this.loggedInUser.getLoggedUser().subscribe(
      (user => {
        this.pageInfo.isAdmin = user.roles.includes('A');
        this.headerAssignation();
      }),
      (error => {
        this.pageInfo.isAdmin = false;
        this.headerAssignation();
      })
    );
  }

  private headerAssignation(): void {
    this.pageInfo.headerValue = this.isType1 ? 1 : this.headerType2.includes(this.router.url) ? 2 : 0;
    this.pageInfo.loading = false;
  }
}
