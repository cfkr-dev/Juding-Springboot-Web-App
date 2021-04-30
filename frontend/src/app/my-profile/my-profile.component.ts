import {Component, OnInit} from '@angular/core';
import {LoggedInUserService} from '../logged-in-user.service';
import {User} from '../user/user.model';
import {BeltService} from '../auxTypes/belt.service';
import {GenderService} from '../auxTypes/gender.service';
import {RefereeRangeService} from '../auxTypes/refereeRange.service';
import {HttpClient} from '@angular/common/http';
import {ImageService} from '../image.service';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['../../assets/vendor/bootstrap/css/bootstrap.min.css', '../../assets/vendor/font-awesome/css/all.css', '../../assets/css/style.css', '../../assets/css/header.css', '../../assets/css/bootstrapAccomodations.css', '../../assets/css/responsiveTable.css', '../../assets/css/profiles.css']
})
export class MyProfileComponent implements OnInit {

  userInfo: User;
  loading: boolean;

  constructor(private loggedUser: LoggedInUserService,
              public beltService: BeltService,
              public refereeRangeService: RefereeRangeService,
              public genderService: GenderService) {
    this.userInfo = undefined;
    this.loading = true;
  }

  ngOnInit(): void {
    this.loggedUser.getLoggedUser().subscribe(
      (userInfo => {
        this.userInfo = userInfo;
        this.loggedUser.getLoggedUserImage(userInfo.roles.includes('C') ? 'competitors' : 'referees', userInfo.licenseId).subscribe(
          ((image: Blob) => {
              const reader = new FileReader();
              reader.addEventListener('load', () => {
                this.userInfo.imageFile = reader.result.toString();
              }, false);
              if (image) {
                reader.readAsDataURL(image);
              }
              this.loading = false;
            }
          )
        );
      })
    );
  }

}
