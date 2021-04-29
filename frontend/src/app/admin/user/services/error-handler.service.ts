import {Injectable} from '@angular/core';
import {throwError} from 'rxjs';
import {Router} from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class ErrorHandlerService {

    constructor(private router: Router) {
    }

    public handleError(error: any): any {
        switch (error.status) {
            case 400: {
                return true;
            }
            case 403: {
                this.router.navigate(['403']);
                break;
            }
            case 404: {
                this.router.navigate(['404']);
                break;
            }
            case 500: {
                this.router.navigate(['500']);
                break;
            }
        }
    }
}
