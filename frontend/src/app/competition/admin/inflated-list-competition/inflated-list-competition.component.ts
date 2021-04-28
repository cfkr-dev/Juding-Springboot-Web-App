import {Component, OnInit} from '@angular/core';
import {CompetitionService} from '../../competition.service';
import {Competition} from '../../competition.model';
import {LoggedInUserService} from '../../../logged-in-user.service';
import {User} from '../../../user/user.model';


@Component({
    selector: 'app-inflated-list-competition',
    templateUrl: './inflated-list-competition.component.html',
    providers: [CompetitionService],
    styleUrls: ['../../../../assets/vendor/font-awesome/css/all.css', '../../../../assets/vendor/bootstrap/v4/css/bootstrap.css', '../../../../assets/css/header.css', '../../../../assets/css/responsiveTable.css', '../../../../assets/css/competitionController.css', '../../../../assets/css/beltAssignations.css']
})
export class InflatedListCompetitionComponent implements OnInit {
    user: User;
    competitions: Competition[];
    page: number;
    latest: boolean;

    constructor(public competitionService: CompetitionService, public loginInUserService: LoggedInUserService) {
    }

    ngOnInit(): void {
        this.loginInUserService.getLoggedUser().subscribe(
            user => {
                console.error(user.roles);
                if (user.roles[0] === 'R' && user.roles[1] === 'A') {
                    this.competitionService.getCompetitionPage(0).subscribe(
                        competitions => {
                            this.competitions = competitions.content;
                            this.latest = competitions.last;
                        },
                        error => console.error(error),
                    );
                }
            },
            error => console.error(error),
        );
    }

    showPage(): void {
        this.page = this.page + 1;
        this.competitionService.getCompetitionPage(this.page).subscribe(
            competitions => {
                this.competitions = competitions;
                this.latest = competitions.last;
            },
            error => console.error(error),
        );
    }
}



