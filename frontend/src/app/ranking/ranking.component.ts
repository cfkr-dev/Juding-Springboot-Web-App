import {Component, OnInit} from '@angular/core';
import {RankingInterface} from './RankingInterface';
import {HttpClient} from '@angular/common/http';
import {LoggedInUserService} from '../logged-in-user.service';
import {UserInterface} from '../user/user.interface';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {RankingService} from './ranking.service';

@Component({
    selector: 'app-ranking',
    templateUrl: './ranking.component.html',
    providers: [RankingService],
    styleUrls: ['../../assets/vendor/bootstrap/v4/css/bootstrap.css', '../../assets/vendor/font-awesome/css/all.css', '../../assets/css/style.css', '../../assets/css/header.css', '../../assets/css/bootstrapAccomodations.css', '../../assets/css/responsiveTable.css', '../../assets/css/profiles.css', '../../assets/css/beltAssignations.css']
})
export class RankingComponent implements OnInit {

    modalMember: RankingInterface;
    ranking: RankingInterface[];

    constructor(private service: RankingService, private loggedUser: LoggedInUserService, private modalService: NgbModal) {
    }

    open(i: number, content): void {
        this.modalMember = this.ranking[i];
        this.service.getUserImage(this.modalMember.licenseId).subscribe(
            ((image: Blob) => {
                    const reader = new FileReader();
                    reader.addEventListener('load', () => {
                        this.modalMember.imageFile = reader.result.toString();
                    }, false);
                    if (image) {
                        reader.readAsDataURL(image);
                    }
                    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'lg'});
                }
            )
        );
    }

    ngOnInit(): void {
        this.loggedUser.getLoggedUser().subscribe(
            ((currentUser: UserInterface) => {
                this.service.getRanking().subscribe(
                    ((ranking: RankingInterface[]) => this.ranking = ranking)
                );
            })
        );
    }
}
