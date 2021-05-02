import {Component, OnInit} from '@angular/core';
import {CompetitionService} from '../../competition.service';
import {User} from '../../../user/user.model';
import {RefereeService} from '../../../admin/user/services/referee.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Competition} from '../../competition.model';
import {DatePipe} from '@angular/common';
import {DatepickerService} from '../../../auxTypes/datepicker.service';
import {NgbDateParserFormatter, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';

interface CompetitionInterface {
    idCompetition?: string | number;
    shortName: string;
    additionalInfo: string;
    minWeight: string | number;
    maxWeight: string | number;
    startDate: string | Date;
    endDate: string | Date;
    referee: string | User;
}

@Component({
    templateUrl: './competition-form.html',
    styleUrls: ['../../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../../assets/vendor/font-awesome/css/all.css',
        '../../../../assets/css/bootstrapAccomodations.css',
        '../../../../assets/css/loginAndRegistration.css'
    ],
    providers: [CompetitionService,
        {provide: NgbDateParserFormatter, useClass: DatepickerService},
        DatepickerService
    ]
})
export class CompetitionFormComponent {
    loadedPage: boolean;
    referees: User[];
    validation: string;
    competition: CompetitionInterface;
    alert: boolean;
    modelS: NgbDateStruct;
    modelE: NgbDateStruct;

    constructor(private competitionService: CompetitionService, private refereeService: RefereeService, private router: Router, private activatedRoute: ActivatedRoute, private datepickerService: DatepickerService) {
        this.alert = false;
        this.validation = 'needs-validation';
        this.loadedPage = false;
        this.modelS = undefined;
        this.modelE = undefined;
        const id = this.activatedRoute.snapshot.params.id;
        if (id) {
            refereeService.getRefereeList().subscribe(
                referees => {
                    this.referees = referees;
                    competitionService.getCompetition(id).subscribe(
                        competition => {
                            this.competition = competition;
                            this.competition.referee = competition.referee.licenseId;
                            this.modelS = this.datepickerService.parse(competition.formattedStartDate, true);
                            this.modelE = this.datepickerService.parse(competition.formattedEndDate, true);
                            this.loadedPage = true;
                        }
                    );
                },
                error => this.router.navigate(['/404'])
            );
        } else {
            refereeService.getRefereeList().subscribe(
                referees => {
                    this.referees = referees;
                    this.competition = {
                        additionalInfo: '',
                        shortName: '',
                        startDate: '',
                        endDate: '',
                        minWeight: '',
                        maxWeight: '',
                        referee: null
                    };
                    this.loadedPage = true;
                },
                error => this.router.navigate(['/404'])
            );
        }
    }

    submitForm(event: Event): void {
        event.preventDefault();
        this.validation = 'was-validated';
        this.competition.idCompetition = (this.competition.idCompetition) ? this.competition.idCompetition : null;
        this.competition.startDate = this.datepickerService.formatAlt(this.modelS);
        this.competition.endDate = this.datepickerService.formatAlt(this.modelE);
        this.competition.minWeight = +this.competition.minWeight;
        this.competition.maxWeight = +this.competition.maxWeight;
        this.competitionService.saveCompetition(this.competition).subscribe(
            competition => this.router.navigate(['/admin/competitions']),
            error => this.alert = true
        );
    }

    updateStartDate(): void {
        this.competition.startDate = this.datepickerService.formatAlt(this.modelS);
    }

    updateEndDate(): void {
        this.competition.endDate = this.datepickerService.formatAlt(this.modelE);
    }
}
