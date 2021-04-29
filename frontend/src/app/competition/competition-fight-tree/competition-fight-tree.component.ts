import {Component, Input} from '@angular/core';
import {Fight} from '../../fight/fight.model';

@Component({
    selector: 'app-competition-fight-tree',
    templateUrl: './competition-fight-tree.html',
    styleUrls: ['../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../assets/vendor/font-awesome/css/all.css',
        '../../../assets/vendor/aos/aos.css',
        '../../../assets/css/style.css',
        '../../../assets/css/header.css',
        '../../../assets/css/bootstrapAccomodations.css',
        '../../../assets/css/responsiveTable.css',
        '../../../assets/css/competitionController.css',
        '../../../assets/css/competitionScreen.css',
        '../../../assets/css/beltAssignations.css']
})
export class CompetitionFightTreeComponent {

    @Input()
    fightsList: Fight[];
}
