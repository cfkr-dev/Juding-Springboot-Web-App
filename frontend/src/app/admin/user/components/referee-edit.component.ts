import {Component, OnInit} from '@angular/core';
import {User} from '../../../user/user.model';
import {ActivatedRoute, Router} from '@angular/router';
import {RefereeService} from '../services/referee.service';
import {getTransformedQueryCallExpr} from '@angular/core/schematics/migrations/static-queries/transform';

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
    loadingContent: boolean;
    submitted: boolean;

    constructor(private router: Router, activatedRoute: ActivatedRoute, private refereeService: RefereeService) {
        this.loadingContent = true;
        const licenseId = activatedRoute.snapshot.params['licenseId'];
        refereeService.getReferee(licenseId).subscribe(
            user => {
                this.loadingContent = false;
                this.user = user;
                this.errorOnLoadUserData = false;
            },
            error => {
                this.errorOnLoadUserData = true;
                this.router.navigate(['500']);
            }
        );
        this.submitted = false;
    }

    ngOnInit(): void {
    }

    modifyReferee(user) {
        this.submitted = true;
        this.refereeService.updateReferee(user).subscribe(
            modifiedUser => this.router.navigate(['/admin/referees']),
            // error => this.validationError = this.errorHandlerService.handleError(error)
        );
    }

}
