import {Injectable} from '@angular/core';
import {CanActivate, Router} from "@angular/router";
import {LoggedInUserService} from "../logged-in-user.service";
import {Observable} from "rxjs";
import {User} from "../user/user.model";
import {map} from "rxjs/operators";

@Injectable()
export class AdminActivationService implements CanActivate {

    constructor(private loggedUser: LoggedInUserService, private router: Router) {
    }

    canActivate(): Observable<boolean> {
        return this.loggedUser.getLoggedUser().pipe(map((response: User) => {
            if (response.roles.includes('A')) {
                return true;
            } else {
                this.router.navigate(['/403']);
                return false;
            }
        })) as Observable<boolean>;
    }
}
