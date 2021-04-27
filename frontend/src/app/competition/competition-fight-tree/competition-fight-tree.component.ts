import {Component, Input} from '@angular/core';
import {Fight} from '../../fight/fight.model';

@Component({
    selector: 'app-competition-fight-tree',
    templateUrl: './competition-fight-tree.html'
})
export class CompetitionFightTreeComponent {

    @Input()
    fightsList: Fight[];
}
