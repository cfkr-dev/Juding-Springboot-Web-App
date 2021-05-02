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
    id?: string;
    shortName: string;
    additionalInfo: string;
    minWeight: number;
    maxWeight: number;
    startDate: string;
    endDate: string;
    referee: string;
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
    competition: Competition;
    validation: string;
    competitionToSubmit: CompetitionInterface;
    alert: boolean;
    modelS: NgbDateStruct;
    modelE: NgbDateStruct;

    constructor(private competitionService: CompetitionService, private refereeService: RefereeService, private router: Router, private activatedRoute: ActivatedRoute, private datepickerService: DatepickerService) {
        this.competitionToSubmit = {
            additionalInfo: '',
            shortName: '',
            startDate: '',
            endDate: '',
            minWeight: 0,
            maxWeight: 0,
            referee: ''
        };
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
                    this.competition = new Competition('', '', 0, 0, undefined, undefined, new User());
                    this.loadedPage = true;
                },
                error => this.router.navigate(['/404'])
            );
        }
    }

    submitForm(event: Event): void {
        event.preventDefault();
        this.validation = 'was-validated';
        this.competitionToSubmit.id = (this.competition.idCompetition) ? this.competition.idCompetition.toString() : null;
        this.competitionToSubmit.startDate = this.datepickerService.formatAlt(this.modelS);
        this.competitionToSubmit.endDate = this.datepickerService.formatAlt(this.modelE);
        this.competitionToSubmit.shortName = this.competition.shortName;
        this.competitionToSubmit.additionalInfo = this.competition.additionalInfo;
        this.competitionToSubmit.minWeight = this.competition.minWeight;
        this.competitionToSubmit.maxWeight = this.competition.maxWeight;
        this.competitionToSubmit.referee = this.competition.referee.licenseId;
        this.competitionService.saveCompetition(this.competitionToSubmit).subscribe(
            competition => this.router.navigate(['/admin/competitions']),
            error => this.alert = true
        );
    }

    updateStartDate(): void {
        this.competitionToSubmit.startDate = this.datepickerService.formatAlt(this.modelS);
    }

    updateEndDate(): void {
        this.competitionToSubmit.endDate = this.datepickerService.formatAlt(this.modelE);
    }
}
