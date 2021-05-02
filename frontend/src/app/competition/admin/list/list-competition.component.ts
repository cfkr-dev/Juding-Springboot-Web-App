import {Component, OnInit} from '@angular/core';
import {CompetitionService} from '../../competition.service';
import {User} from '../../../user/user.model';
import {LoggedInUserService} from '../../../logged-in-user.service';
import {Competition} from '../../competition.model';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Router} from '@angular/router';

@Component({
    selector: 'app-list',
    templateUrl: './list-competition.component.html',
    providers: [CompetitionService],
    styleUrls: ['../../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../../assets/vendor/font-awesome/css/all.css',
        '../../../../assets/vendor/aos/aos.css',
        '../../../../assets/css/style.css',
        '../../../../assets/css/header.css',
        '../../../../assets/css/bootstrapAccomodations.css',
        '../../../../assets/css/responsiveTable.css',
        '../../../../assets/css/adminScreen.css',
        '../../../../assets/css/competitionController.css',
        '../../../../assets/css/beltAssignations.css']
})
export class ListCompetitionComponent implements OnInit {
    user: User;
    competitions: Competition[];
    latest: number;
    empty: boolean;
    page: number;
    totalPages: number;

    modalMember: Competition;
    identifier: number;
    position: number;

    fullLoaded: boolean;
    loading: boolean;

    constructor(public competitionService: CompetitionService,
                public loginInUserService: LoggedInUserService,
                private modalService: NgbModal,
                private router: Router) {
    }

    ngOnInit(): void {
        this.loginInUserService.getLoggedUser().subscribe(
            user => {
                if (user.roles.includes('A')) {
                    this.page = 0;
                    this.competitionService.getCompetitionPage(this.page).subscribe(
                        competitions => {
                            this.page = this.page + 1;
                            this.competitions = competitions.content;
                            this.latest = competitions.last;
                            this.empty = this.competitionService.haveCompetitions(this.competitions);
                            this.totalPages = competitions.totalPages;
                            this.fullLoaded = true;
                        },
                        error => this.router.navigate(['500']),
                    );
                }
            }
        );
    }


    showPage(): void {
        this.loading = true;
        this.competitionService.getCompetitionPage(this.page).subscribe(
            competitions => {
                this.loading = false;
                this.page = this.page + 1;
                for (let i = 0; i < competitions.numberOfElements; i++) {
                    this.competitions.push(competitions.content[i]);
                }
                this.latest = competitions.last;
            },
            error => this.router.navigate(['500']),
        );
    }

    showInfo(i: number, info): void {
        this.modalMember = this.competitions[i];
        this.modalService.open(info, {ariaLabelledBy: 'modal-basic-title', size: 'lg'});
    }

    showDelete(i: number, modal): void {
        this.modalMember = this.competitions[i];
        this.identifier = this.competitions[i].idCompetition;
        this.position = i;
        this.modalService.open(modal, {ariaLabelledBy: 'modal-basic-title'});
    }

    deleteCompetition(): void {
        this.competitionService.deleteCompetition(this.identifier).subscribe(
            competitions => {
                this.competitions.splice(this.position, 1);
                this.modalService.dismissAll();
                this.ngOnInit();
            },
            error => this.router.navigate(['500']),
        );
    }

}
