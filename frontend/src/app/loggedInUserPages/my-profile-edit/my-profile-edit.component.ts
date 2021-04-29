import {Component, OnInit} from '@angular/core';
import {UserInterface} from '../../user/user.interface';
import {LoggedInUserService} from '../../logged-in-user.service';
import {BeltService} from '../../auxTypes/belt.service';
import {RefereeRangeService} from '../../auxTypes/refereeRange.service';
import {MyProfileEditService} from './my-profile-edit.service';
import {ImageService} from '../../image.service';
import {Router} from '@angular/router';

export interface ProfileEditForm {
  licenseId: string;
  belt: string;
  gym?: string;
  weight?: string;
  refereeRange?: string;
  phone: string;
  email: string;
}

@Component({
  selector: 'app-my-profile-edit',
  templateUrl: './my-profile-edit.component.html',
  styleUrls: ['../../../assets/vendor/bootstrap/css/bootstrap.min.css', '../../../assets/vendor/font-awesome/css/all.css', '../../../assets/css/style.css', '../../../assets/css/header.css', '../../../assets/css/bootstrapAccomodations.css', '../../../assets/css/responsiveTable.css', '../../../assets/css/profiles.css'],
  providers: [MyProfileEditService]
})
export class MyProfileEditComponent implements OnInit {

  userInfo: UserInterface;
  error: boolean;
  editProfileForm: ProfileEditForm;
  fileInput: string;

  constructor(private router: Router,
              private loggedUser: LoggedInUserService,
              public beltService: BeltService,
              public refereeRangeService: RefereeRangeService,
              private editService: MyProfileEditService,
              public imageService: ImageService) {
    this.editProfileForm = {belt: '', email: '', gym: '', licenseId: '', phone: '', weight: ''};
    this.error = false;
  }

  ngOnInit(): void {
    this.loggedUser.getLoggedUser().subscribe(
      ((user: UserInterface) => {
        this.userInfo = user;
        this.editProfileForm.belt = user.belt;
        this.editProfileForm.email = user.email;
        this.editProfileForm.gym = user.gym;
        this.editProfileForm.refereeRange = user.refereeRange;
        this.editProfileForm.licenseId = user.licenseId;
        this.editProfileForm.phone = user.phone.toString();
        this.editProfileForm.weight = user.weight.toString();
      })
    );
  }

  submitForm(event: Event): void {
    event.preventDefault();
    const roleString = this.userInfo.roles.includes('C') ? 'competitors' : 'referees';
    this.editService.sendData(this.editProfileForm, roleString).subscribe(
      ((savedUser: UserInterface) => {
        if (this.fileInput !== undefined) {
          this.imageService.onUpload('/api/' + roleString + '/' + this.editProfileForm.licenseId + '/image').subscribe(
            (successful => {
            }),
            (error => this.error = true)
          );
        }
        this.router.navigate(['/myProfile']);
      }),
      (error => this.error = true)
    );
  }
}
