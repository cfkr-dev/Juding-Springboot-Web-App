import {Component} from '@angular/core';
import {Competition} from '../competition.model';
import {CompetitionService} from '../competition.service';
import {ActivatedRoute} from '@angular/router';
import {Fight} from '../../fight/fight.model';

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
        '../../../assets/css/competitionController.css',
        '../../../assets/css/beltAssignations.css']
})
export class CompetitionDetailComponent{
    competition: Competition;
    people: number;
    fightslist: Fight[];

    constructor(activatedRouter: ActivatedRoute, public service: CompetitionService) {
        const id = activatedRouter.snapshot.params.id;
        service.getCompetition(id).subscribe(
            competition => {
                this.competition = competition;
                this.people = service.getPeople(this.competition);
                this.fightslist = competition.fights;
            },
            error => console.error(error),
        );
    }


}
