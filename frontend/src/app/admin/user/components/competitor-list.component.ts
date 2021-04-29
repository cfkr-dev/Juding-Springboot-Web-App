import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {Router} from '@angular/router';
import {User} from '../models/user.model';
import {CompetitorService} from '../services/competitor.service';
import {ErrorHandlerService} from '../services/error-handler.service';


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
    ]
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

    constructor(private router: Router, private competitorService: CompetitorService, private modalService: NgbModal, private errorHandlerService: ErrorHandlerService) {
        this.hasErrorOnLoad = false;
        this.isLastPage = false;
        this.noMorePages = false;
        this.errorOnRemoving = false;
        this.currentPage = 0;
    }

    ngOnInit() {
        this.competitorService.getCompetitors(0).subscribe(
            pageOfUsers => {
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
                this.errorHandlerService.handleError(error);
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
                    this.errorHandlerService.handleError(error);
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
                if (this.users.length === 0){
                    this.noMorePages = true;
                }
                const currentUrl = this.router.url;
                this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
                    this.router.navigate([currentUrl]);
                });
            },
            error => {
                this.errorOnRemoving = true;
                this.errorHandlerService.handleError(error);
            }
        );
    }

    openModal(content) {
        this.modalService.open(content, {size: 'lg'});
    }
}
