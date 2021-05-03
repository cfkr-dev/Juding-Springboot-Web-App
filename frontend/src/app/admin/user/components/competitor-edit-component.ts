import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CompetitorService} from '../services/competitor.service';
import {User} from '../../../user/user.model';
import {NgbDateParserFormatter, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import {DatepickerService} from '../../../auxTypes/datepicker.service';
import {ValidationService} from '../services/validation.service';

@Component({
    selector: 'app-competitor-edit-component',
    templateUrl: './competitor-edit-component.html',
    styleUrls: ['../../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../../assets/vendor/datepicker/jquery.datetimepicker.min.css',
        '../../../../assets/vendor/font-awesome/css/all.css',
        '../../../../assets/css/header.css',
        '../../../../assets/css/bootstrapAccomodations.css',
        '../../../../assets/css/loginAndRegistration.css'
    ],
    providers: [
        CompetitorService,
        ValidationService,
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
    @ViewChild('form') form: ElementRef;

    constructor(private router: Router, activatedRoute: ActivatedRoute, private competitorService: CompetitorService, private datepickerService: NgbDateParserFormatter, private validation: ValidationService) {
        this.loadingContent = true;
        const licenseId = activatedRoute.snapshot.params['licenseId'];
        competitorService.getCompetitor(licenseId).subscribe(
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
        if (this.form.nativeElement.checkValidity()) {
            this.validation.checkNickname(user).subscribe(
                ((validationResult: boolean) => {
                    if (!validationResult) {
                        this.validationError = false;
                        this.competitorService.updateCompetitor(user).subscribe(
                            (successful => this.router.navigate(['/admin/competitors'])),
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
