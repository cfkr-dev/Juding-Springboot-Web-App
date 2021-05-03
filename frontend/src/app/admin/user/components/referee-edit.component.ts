import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {User} from '../../../user/user.model';
import {ActivatedRoute, Router} from '@angular/router';
import {RefereeService} from '../services/referee.service';
import {NgbDateParserFormatter, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {DatepickerService} from '../../../auxTypes/datepicker.service';
import {ValidationService} from "../services/validation.service";

@Component({
    selector: 'app-referee-edit',
    templateUrl: './referee-edit.component.html',
    styleUrls: ['../../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../../assets/vendor/datepicker/jquery.datetimepicker.min.css',
        '../../../../assets/vendor/font-awesome/css/all.css',
        '../../../../assets/css/header.css',
        '../../../../assets/css/bootstrapAccomodations.css',
        '../../../../assets/css/loginAndRegistration.css'
    ],
    providers: [
        RefereeService,
        ValidationService,
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
    @ViewChild('form') form: ElementRef;

    constructor(private router: Router, activatedRoute: ActivatedRoute, private refereeService: RefereeService, private datepickerService: NgbDateParserFormatter, private validation: ValidationService) {
        this.loadingContent = true;
        const licenseId = activatedRoute.snapshot.params['licenseId'];
        refereeService.getReferee(licenseId).subscribe(
            user => {
                this.loadingContent = false;
                this.user = user;
                this.errorOnLoadUserData = false;
                this.signUpFormBirthdate = this.getBirthdate(user.birthDate);
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
        if (this.form.nativeElement.checkValidity()) {
            this.validation.checkNickname(user).subscribe(
                ((validationResult: boolean) => {
                    if (!validationResult) {
                        this.validationError = false;
                        this.refereeService.updateReferee(user).subscribe(
                            (successful => this.router.navigate(['/admin/referees'])),
                            error => this.router.navigate(['500'])
                        );
                    } else {
                        this.validationError = true;
                    }
                }),
                error => this.router.navigate(['500'])
            );
        } else {
            this.validationError = false;
        }
    }

    updateBirthdate() {
        this.user.birthDate = this.datepickerService.format(this.signUpFormBirthdate);
    }

}
