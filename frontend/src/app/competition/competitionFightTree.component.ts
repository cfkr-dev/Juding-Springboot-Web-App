import {Component, Input} from "@angular/core";
import {Fight} from "../fight/fight.model";

@Component({
    selector: 'competitionFightTree',
    templateUrl: 'competitionFightTree.html'
})
export class CompetitionFightTreeComponent {

    @Input()
    fightsList: Fight[];
}
