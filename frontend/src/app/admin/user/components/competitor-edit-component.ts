import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CompetitorService} from '../services/competitor.service';
import {User} from '../../../user/user.model';
import {ErrorHandlerService} from '../services/error-handler.service';

@Component({
    selector: 'app-competitor-edit-component',
    templateUrl: './competitor-edit-component.html',
    styleUrls: ['../../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../../assets/vendor/datepicker/jquery.datetimepicker.min.css',
        '../../../../assets/vendor/font-awesome/css/all.css',
        '../../../../assets/css/header.css',
        '../../../../assets/css/bootstrapAccomodations.css'
    ]
})
export class CompetitorEditComponent implements OnInit {

    user: User;
    errorOnLoadUserData: boolean;
    validationError: boolean;

    constructor(private router: Router, activatedRoute: ActivatedRoute, private competitorService: CompetitorService, private errorHandlerService: ErrorHandlerService) {
        const licenseId = activatedRoute.snapshot.params['licenseId'];
        competitorService.getCompetitor(licenseId).subscribe(
            user => {
                this.user = user;
                this.errorOnLoadUserData = false;
            },
            error => {
                this.errorOnLoadUserData = true;
                this.errorHandlerService.handleError(error);
            }
        );
        this.validationError = false;
    }

    ngOnInit(): void {
    }

    modifyCompetitor(user) {
        this.competitorService.updateCompetitor(user).subscribe(
            modifiedUser => this.router.navigate(['/competitors']),
            // error => this.validationError = this.errorHandlerService.handleError(error)
        );
    }


}
