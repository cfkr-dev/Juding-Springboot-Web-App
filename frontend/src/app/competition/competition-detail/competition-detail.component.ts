import {Component} from '@angular/core';
import {Competition} from '../competition.model';
import {CompetitionService} from '../competition.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Fight} from '../../fight/fight.model';
import {BeltService} from '../../auxTypes/belt.service';
import {GenderService} from '../../auxTypes/gender.service';
import { OnInit } from '@angular/core';
import {LoggedInUserService} from '../../logged-in-user.service';

@Component({
    selector: 'app-competition-detail',
    templateUrl: './competition-detail.html',
    providers: [CompetitionService],
    styleUrls: ['../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../assets/vendor/font-awesome/css/all.css',
        '../../../assets/vendor/aos/aos.css',
        '../../../assets/css/style.css',
        '../../../assets/css/header.css',
        '../../../assets/css/bootstrapAccomodations.css',
        '../../../assets/css/responsiveTable.css',
        '../../../assets/css/competitionScreen.css',
        '../../../assets/css/competitionController.css',
        '../../../assets/css/beltAssignations.css']
})
export class CompetitionDetailComponent implements OnInit {
    competition: Competition;
    people: number;
    fightsList: Fight[];
    id: number;
    fullLoaded: boolean;

    constructor(private router: Router, activatedRouter: ActivatedRoute, public competitionService: CompetitionService, public beltService: BeltService, public genderService: GenderService, public loginInUserService: LoggedInUserService) {
        this.id = activatedRouter.snapshot.params.id;
    }

    ngOnInit(): void {
        this.loginInUserService.getLoggedUser().subscribe(
            user => {
                if (user.roles.includes('C')) {
                    this.competitionService.getCompetition(this.id).subscribe(
                        competition => {
                            this.competition = competition;
                            this.people = this.competitionService.getPeople(this.competition);
                            this.fightsList = competition.fights;
                            this.fullLoaded = true;
                        },
                        error => this.router.navigate(['/**']),
                    );
                } else if (user.roles.includes('R')) {
                    this.competitionService.getCompetition(this.id).subscribe(
                        competition => {
                            this.competition = competition;
                            this.people = this.competitionService.getPeople(this.competition);
                            this.fightsList = competition.fights;
                            this.fullLoaded = true;
                        },
                        error => this.router.navigate(['/**']),
                    );
                }
            },
                error => this.router.navigate(['/403']),
            );
    }


}
