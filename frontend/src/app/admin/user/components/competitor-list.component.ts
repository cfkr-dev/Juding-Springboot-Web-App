import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Router} from '@angular/router';
import {User} from '../../../user/user.model';
import {CompetitorService} from '../services/competitor.service';


@Component({
    selector: 'app-competitors-list',
    templateUrl: 'competitor-list.component.html',
    styleUrls: ['../../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../../assets/vendor/aos/aos.css',
        '../../../../assets/vendor/font-awesome/css/all.css',
        '../../../../assets/css/style.css',
        '../../../../assets/css/header.css',
        '../../../../assets/css/profiles.css',
        '../../../../assets/css/bootstrapAccomodations.css',
        '../../../../assets/css/responsiveTable.css',
        '../../../../assets/css/adminScreen.css',
        '../../../../assets/css/beltAssignations.css'
    ],
    providers: [CompetitorService]
})
export class CompetitorListComponent implements OnInit {

    users: User[];
    hasErrorOnLoad: boolean;
    isLastPage: boolean;
    currentPage: number;
    noMorePages: boolean;
    totalPages: number;
    loading: boolean;
    errorOnRemoving: boolean;
    loadingContent: boolean;

    constructor(private router: Router, private competitorService: CompetitorService, private modalService: NgbModal) {
    }

    ngOnInit() {
        this.hasErrorOnLoad = false;
        this.isLastPage = false;
        this.noMorePages = false;
        this.errorOnRemoving = false;
        this.loadingContent = false;
        this.currentPage = 0;
        this.competitorService.getCompetitors(0).subscribe(
            pageOfUsers => {
                this.loadingContent = false;
                if (Object.keys(pageOfUsers).length === 0) {
                    this.noMorePages = true;
                    this.isLastPage = true;
                } else {
                    this.users = pageOfUsers.content;
                    this.isLastPage = pageOfUsers.last;
                    this.totalPages = pageOfUsers.totalPages;
                }
            },
            error => {
                this.hasErrorOnLoad = true;
                this.router.navigate(['500']);
            }
        );
        this.currentPage = 1;
    }

    loadMoreCompetitors(page) {
        if (!this.isLastPage) {
            this.loading = true;
            this.competitorService.getCompetitors(page).subscribe(
                pageOfUsers => {
                    this.loading = false;
                    this.users = this.users.concat(pageOfUsers.content);
                    this.isLastPage = pageOfUsers.last;
                },
                error => {
                    this.hasErrorOnLoad = true;
                    this.router.navigate(['500']);
                }
            );
            this.currentPage += 1;
        }
    }

    removeCompetitor(user) {
        this.competitorService.removeCompetitor(user).subscribe(
            removedUser => {
                this.users.forEach((element, index) => {
                    if (element.licenseId === removedUser.licenseId) {
                        this.users.splice(index);
                    }
                });
                if (this.users.length === 0) {
                    this.noMorePages = true;
                }
                this.ngOnInit();
            },
            error => {
                this.errorOnRemoving = true;
                this.router.navigate(['500']);
            }
        );
    }

    openModal(content) {
        this.modalService.open(content, {size: 'lg'});
    }
}
