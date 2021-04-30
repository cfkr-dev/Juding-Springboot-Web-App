import {Component, OnInit} from '@angular/core';
import {User} from '../../../user/user.model';
import {ActivatedRoute, Router} from '@angular/router';
import {ErrorHandlerService} from '../services/error-handler.service';
import {RefereeService} from '../services/referee.service';

@Component({
    selector: 'app-referee-edit',
    templateUrl: './referee-edit.component.html',
    styleUrls: ['../../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../../assets/vendor/datepicker/jquery.datetimepicker.min.css',
        '../../../../assets/vendor/font-awesome/css/all.css',
        '../../../../assets/css/header.css',
        '../../../../assets/css/bootstrapAccomodations.css'
    ]
})
export class RefereeEditComponent implements OnInit {

    user: User;
    errorOnLoadUserData: boolean;

    constructor(private router: Router, activatedRoute: ActivatedRoute, private refereeService: RefereeService, private errorHandlerService: ErrorHandlerService) {
        const licenseId = activatedRoute.snapshot.params['licenseId'];
        refereeService.getReferee(licenseId).subscribe(
            user => {
                this.user = user;
                this.errorOnLoadUserData = false;
            },
            error => {
                this.errorOnLoadUserData = true;
                this.errorHandlerService.handleError(error);
            }
        );
    }

    ngOnInit(): void {
    }

    modifyReferee(user) {
        this.refereeService.updateReferee(user).subscribe(
            modifiedUser => this.router.navigate(['/referees']),
            // error => this.validationError = this.errorHandlerService.handleError(error)
        );
    }

}
