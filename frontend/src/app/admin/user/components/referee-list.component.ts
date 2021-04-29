import {Component, OnInit} from '@angular/core';
import {User} from '../models/user.model';
import {Router} from '@angular/router';
import {RefereeService} from '../services/referee.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ErrorHandlerService} from '../services/error-handler.service';

@Component({
    selector: 'app-referee-list',
    templateUrl: './referee-list.component.html',
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
export class RefereeListComponent implements OnInit {

    users: User[];
    applicators: User[];
    hasErrorOnLoad: boolean;
    noApplications: boolean;
    isLastPage: boolean;
    currentPage: number;
    noMorePages: boolean;
    totalPages: number;
    loading: boolean;
    errorOnRemoving: boolean;
    errorOnAcceptApplication: boolean;
    userModal: User;

    constructor(private router: Router, private refereeService: RefereeService, private modalService: NgbModal, private errorHandlerService: ErrorHandlerService) {
        this.hasErrorOnLoad = false;
        this.noApplications = false;
        this.isLastPage = false;
        this.noMorePages = false;
        this.errorOnRemoving = false;
        this.errorOnAcceptApplication = false;
        this.currentPage = 0;
    }

    ngOnInit() {
        this.refereeService.getApplications().subscribe(
            pageOfApplicators => {
                if (Object.keys(pageOfApplicators).length === 0) {
                    this.noApplications = true;
                } else {
                    this.applicators = pageOfApplicators;
                }
            },
            error => {
                this.noApplications = true;
                this.errorHandlerService.handleError(error);
            }
        );

        this.refereeService.getReferees(0).subscribe(
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

    loadMoreReferees(page) {
        if (!this.isLastPage) {
            this.loading = true;
            this.refereeService.getReferees(page).subscribe(
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

    removeReferee(user) {
        this.refereeService.removeReferee(user).subscribe(
            removedUser => {
                this.users.forEach((element, index) => {
                    if (element.licenseId === removedUser.licenseId) {
                        this.users.splice(index, 1);
                    }
                });
                if (this.users.length === 0) {
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

    removeApplication(user) {
        this.refereeService.removeReferee(user).subscribe(
            removedUser => {
                this.applicators.forEach((element, index) => {
                    if (element.licenseId === removedUser.licenseId) {
                        this.applicators.splice(index, 1);
                    }
                });
                if (this.applicators.length === 0) {
                    this.noApplications = true;
                }
            },
            error => {
                this.errorOnRemoving = true;
                this.errorHandlerService.handleError(error);
            }
        );
    }

    acceptApplication(user) {
        this.refereeService.admitReferee(user).subscribe(
            acceptUser => {
                this.applicators.forEach((element, index) => {
                    if (element.licenseId === acceptUser.licenseId) {
                        this.applicators.splice(index, 1);
                    }
                });
                if (this.applicators.length === 0) {
                    this.noApplications = true;
                }
            },
            error => {
                this.errorOnAcceptApplication = true;
                this.errorHandlerService.handleError(error);
            }
        );
    }

    openModal(content, user) {
        this.userModal = user;
        this.modalService.open(content, {size: 'lg'});
    }

}
