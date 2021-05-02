import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CompetitorService} from '../services/competitor.service';
import {User} from '../../../user/user.model';
import {NgbDateParserFormatter, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {DatepickerService} from '../../../auxTypes/datepicker.service';
import {FormValidationService} from '../../../auxTypes/form-validation.service';

@Component({
    selector: 'app-competitor-edit-component',
    templateUrl: './competitor-edit-component.html',
    styleUrls: ['../../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../../assets/vendor/datepicker/jquery.datetimepicker.min.css',
        '../../../../assets/vendor/font-awesome/css/all.css',
        '../../../../assets/css/header.css',
        '../../../../assets/css/bootstrapAccomodations.css'
    ],
    providers: [
        CompetitorService,
        {provide: NgbDateParserFormatter, useClass: DatepickerService}
    ]
})
export class CompetitorEditComponent implements OnInit {

    user: User;
    errorOnLoadUserData: boolean;
    validationError: boolean;
    loadingContent: boolean;
    signUpFormBirthdate: NgbDateStruct;
    submitted: boolean;

    constructor(private router: Router, activatedRoute: ActivatedRoute, private competitorService: CompetitorService, private datepickerService: NgbDateParserFormatter, private formValidaionService: FormValidationService) {
        this.loadingContent = true;
        const licenseId = activatedRoute.snapshot.params['licenseId'];
        competitorService.getCompetitor(licenseId).subscribe(
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
        this.validationError = false;
        this.submitted = false;
    }

    ngOnInit(): void {
    }

    private getBirthdate(date): NgbDateStruct {
        let reformattedDate = date.split('/');
        return {
            year: parseInt(reformattedDate[2]),
            month: parseInt(reformattedDate[1]),
            day: parseInt(reformattedDate[0])
        };
    }

    modifyCompetitor(user) {
        this.submitted = true;
        this.competitorService.updateCompetitor(user).subscribe(
            modifiedUser => {
                this.formValidaionService.checkValidity(modifiedUser).subscribe(
                    validationResult => {
                        if (validationResult === true) {
                            this.validationError = true;
                        } else {
                            this.validationError = false;
                        }
                    }
                );
                this.router.navigate(['/admin/competitors']);
            },
            error => this.router.navigate(['500'])
        );
    }

    updateBirthdate() {
        this.user.birthdate = this.datepickerService.format(this.signUpFormBirthdate);
    }


}
