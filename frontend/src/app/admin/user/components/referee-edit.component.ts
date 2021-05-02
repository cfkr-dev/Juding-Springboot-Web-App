import {Component, OnInit} from '@angular/core';
import {User} from '../../../user/user.model';
import {ActivatedRoute, Router} from '@angular/router';
import {RefereeService} from '../services/referee.service';
import {NgbDateParserFormatter, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {DatepickerService} from '../../../auxTypes/datepicker.service';
import {FormValidationService} from '../../../auxTypes/form-validation.service';

@Component({
    selector: 'app-referee-edit',
    templateUrl: './referee-edit.component.html',
    styleUrls: ['../../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../../assets/vendor/datepicker/jquery.datetimepicker.min.css',
        '../../../../assets/vendor/font-awesome/css/all.css',
        '../../../../assets/css/header.css',
        '../../../../assets/css/bootstrapAccomodations.css'
    ],
    providers: [
        RefereeService,
        {provide: NgbDateParserFormatter, useClass: DatepickerService}
    ]
})
export class RefereeEditComponent implements OnInit {

    user: User;
    errorOnLoadUserData: boolean;
    loadingContent: boolean;
    signUpFormBirthdate: NgbDateStruct;
    validationError: boolean;
    submitted: boolean;

    constructor(private router: Router, activatedRoute: ActivatedRoute, private refereeService: RefereeService, private datepickerService: NgbDateParserFormatter, private formValidaionService: FormValidationService) {
        this.loadingContent = true;
        const licenseId = activatedRoute.snapshot.params['licenseId'];
        refereeService.getReferee(licenseId).subscribe(
            user => {
                this.loadingContent = false;
                this.user = user;
                this.errorOnLoadUserData = false;
                this.signUpFormBirthdate = this.getBirthdate(user.birthdate);
            },
            error => {
                this.errorOnLoadUserData = true;
                this.router.navigate(['500']);
            }
        );
        this.submitted = false;
        this.validationError = false;
    }

    ngOnInit(): void {
    }

    private getBirthdate(date): NgbDateStruct {
        let reformattedDate = date.split('/');
        return {year: parseInt(reformattedDate[2]), month: parseInt(reformattedDate[1]), day: parseInt(reformattedDate[0])};
    }

    modifyReferee(user) {
        this.submitted = true;
        this.refereeService.updateReferee(user).subscribe(
            modifiedUser => {
                this.formValidaionService.checkValidity(modifiedUser).subscribe(
                    validationResult => {
                        if (validationResult === true){
                            this.validationError = true;
                        } else {
                            this.validationError = false;
                        }
                    }
                );
                this.router.navigate(['/admin/referees']);
            }
        );
    }

    updateBirthdate() {
        this.user.birthdate = this.datepickerService.format(this.signUpFormBirthdate);
    }

}
