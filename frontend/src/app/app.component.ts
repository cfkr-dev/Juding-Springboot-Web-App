import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {LoggedInUserService} from './logged-in-user.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'juding';

    constructor(public router: Router, public loggedInUser: LoggedInUserService) {
    }
}
