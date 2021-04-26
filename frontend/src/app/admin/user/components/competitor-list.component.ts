import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../models/user.model';
import {CompetitorService} from '../services/competitor.service';
import {LoginService} from '../../../login/login.service';


@Component({
  template: `
    <h2>Books</h2>
    <ul class="items">
      <li *ngFor="let user of users">
        {{user.nickname}}
      </li>
      <button (click)="loadMoreCompetitors(currentPage + 1)">Load more</button>
    </ul>
  `
})
export class CompetitorListComponent implements OnInit {

  users: User[];
  currentPage: number;

  constructor(private router: Router, private competitorService: CompetitorService, private loginService: LoginService) {
  }

  ngOnInit() {
    this.loginService.login('Cron', 'Cron2021');
    this.competitorService.getCompetitors(0).subscribe(
      users => this.users = users,
      error => console.log(error)
    );
    this.currentPage = 0;
  }

  loadMoreCompetitors(page) {
    this.competitorService.getCompetitors(page).subscribe(
      users => this.users.concat(users),
      error => console.log(error)
    );
    this.currentPage += 1;
  }
}
