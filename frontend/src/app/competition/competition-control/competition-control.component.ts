import {Component} from '@angular/core';
import {Competition} from '../competition.model';
import {ActivatedRoute} from '@angular/router';
import {CompetitionService} from '../competition.service';
import {Fight} from '../../fight/fight.model';

interface FightsTournament {
    final: Fight[];
    semifinal: Fight[];
    quarterFinals: Fight[];
    roundOfSixteen: Fight[];
}

@Component({
    selector: 'app-competitioncontrol',
    templateUrl: './competition-control.html',
    providers: [CompetitionService],
    styleUrls: ['../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../assets/vendor/font-awesome/css/all.css',
        '../../../assets/vendor/aos/aos.css',
        '../../../assets/css/style.css',
        '../../../assets/css/header.css',
        '../../../assets/css/bootstrapAccomodations.css',
        '../../../assets/css/responsiveTable.css',
        '../../../assets/css/competitionController.css']
})
export class CompetitionControlComponent {
    competition: Competition;
    fights: FightsTournament;
    selectItems: string;

    fullLoaded: boolean;

    constructor(activatedRouter: ActivatedRoute, private service: CompetitionService) {
        const id = activatedRouter.snapshot.params.id;
        this.fights = {final: [], quarterFinals: [], roundOfSixteen: [], semifinal: []};
        service.getCompetition(id).subscribe(
            competition => {
                this.competition = competition;
                for (let fight = 14; fight >= 7; fight --) {
                    this.fights.roundOfSixteen.push(this.competition.fights[fight]);
                }
                for (let fight = 6; fight >= 3; fight --) {
                    this.fights.quarterFinals.push(this.competition.fights[fight]);
                }
                for (let fight = 2; fight >= 1; fight --) {
                    this.fights.semifinal.push(this.competition.fights[fight]);
                }
                this.fights.final.push(this.competition.fights[0]);
                this.fullLoaded = true;
            },
            error => console.error(error),
        );
    }
}



