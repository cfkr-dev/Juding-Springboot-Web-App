import {Component, OnInit} from '@angular/core';
import {RankingInterface} from './RankingInterface';
import {HttpClient} from '@angular/common/http';
import {LoggedInUserService} from '../../logged-in-user.service';
import {UserInterface} from '../../user/user.interface';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-ranking',
  templateUrl: './ranking.component.html',
  styleUrls: ['../../../assets/vendor/bootstrap/v4/css/bootstrap.css', '../../../assets/vendor/font-awesome/css/all.css', '../../../assets/css/style.css', '../../../assets/css/header.css', '../../../assets/css/bootstrapAccomodations.css', '../../../assets/css/responsiveTable.css', '../../../assets/css/profiles.css', '../../../assets/css/beltAssignations.css']
})
export class RankingComponent implements OnInit {

  modalMember: RankingInterface;
  ranking: RankingInterface[];

  constructor(private http: HttpClient, private loggedUser: LoggedInUserService, private modalService: NgbModal) {
  }

  open(i: number, content): void {
    this.modalMember = this.ranking[i];
    this.http.get('/api/competitors/' + this.modalMember.licenseId + '/image', {responseType: 'blob'}).subscribe(
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
        this.http.get('/api/ranking', {withCredentials: true}).subscribe(
          ((ranking: RankingInterface[]) => this.ranking = ranking)
        );
      })
    );
  }
}
