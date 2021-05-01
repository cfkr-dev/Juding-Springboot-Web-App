import {Component} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {LoggedInUserService} from './logged-in-user.service';
import {filter, map} from "rxjs/operators";
import {Title} from "@angular/platform-browser";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {
    title = 'juding';

    constructor(public router: Router, private activatedRoute: ActivatedRoute, private titleService: Title, public loggedInUser: LoggedInUserService) {
        this.router.events.pipe(
            filter(event => event instanceof NavigationEnd),
            map(() => {
                let child = this.activatedRoute.firstChild;
                while (child) {
                    if (child.firstChild) {
                        child = child.firstChild;
                    } else if (child.snapshot.data && child.snapshot.data['title']) {
                        return child.snapshot.data['title'];
                    } else {
                        return null;
                    }
                }
                return null;
            })
        ).subscribe((data: any) => {
            if (data) {
                this.titleService.setTitle(data + ' - Juding');
            }
        });
    }
}
