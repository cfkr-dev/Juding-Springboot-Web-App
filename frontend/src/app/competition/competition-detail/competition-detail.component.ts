import {Component} from '@angular/core';
import {Competition} from '../competition.model';
import {CompetitionService} from '../competition.service';
import {ActivatedRoute} from '@angular/router';
import {Fight} from '../../fight/fight.model';

@Component({
    selector: 'app-competition-detail',
    templateUrl: './competition-detail.html'
})
export class CompetitionDetailComponent{
    competition: Competition;
    people: number;
    fightslist: Fight[];

    constructor(activatedRouter: ActivatedRoute, public service: CompetitionService) {
        let id = activatedRouter.snapshot.params['id'];
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
