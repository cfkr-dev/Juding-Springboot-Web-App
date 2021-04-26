import {Component} from "@angular/core";
import {Competition} from "./competition.model";
import {CompetitionService} from "./competition.service";
import {ActivatedRoute, Route} from "@angular/router";

@Component({
    selector: 'competition-detail',
    templateUrl: 'competition-detail.html'
})
export class CompetitionDetail {
    competition: Competition
    people: number

    constructor(private route: Route, activatedRoute: ActivatedRoute, private service: CompetitionService) {
        let id = activatedRoute.snapshot.params['id'];
        service.getCompetition(id).subscribe(
            competition => {
                this.competition = competition;
                this.people = service.getPeople(this.competition);
            },
            error => console.error(error),
        );
    }
}
