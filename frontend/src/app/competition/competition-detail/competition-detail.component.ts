import {Component} from '@angular/core';
import {Competition} from '../competition.model';
import {CompetitionService} from '../competition.service';
import {ActivatedRoute} from '@angular/router';
import {Fight} from '../../fight/fight.model';
import {BeltService} from '../../auxTypes/belt.service';
import {GenderService} from '../../auxTypes/gender.service';

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
export class CompetitionDetailComponent {
    competition: Competition;
    people: number;
    fightsList: Fight[];

    fullLoaded: boolean;

    constructor(activatedRouter: ActivatedRoute, public competitionService: CompetitionService, public beltService: BeltService, public genderService: GenderService) {
        const id = activatedRouter.snapshot.params.id;
        competitionService.getCompetition(id).subscribe(
            competition => {
                this.competition = competition;
                this.people = competitionService.getPeople(this.competition);
                this.fightsList = competition.fights;
                this.fullLoaded = true;
            },
            error => console.error(error),
        );
    }


}
