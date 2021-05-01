import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SignUpService} from "./sign-up.service";
import {ActivatedRoute, Router} from "@angular/router";
import {GenderService} from "../auxTypes/gender.service";
import {BeltService} from "../auxTypes/belt.service";
import {ImageService} from "../image.service";
import {LoginService} from "../login/login.service";
import {NgbDateParserFormatter, NgbDateStruct} from "@ng-bootstrap/ng-bootstrap";
import {DatepickerService} from "../auxTypes/datepicker.service";

export interface SignUpForm{
    name: string;
    surname: string;
    gender: string;
    phone?: number;
    email: string;
    birthDate: string;
    dni: string;
    licenseId: string;
    nickname: string;
    password: string;
    securityQuestion: string;
    securityAnswer: string;
    belt: string;
    imageFile: string;
    gym?: string;
    weight?: string;
    refereeRange?: string;
}

@Component({
    selector: 'app-sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: ['../../assets/vendor/bootstrap/v4/css/bootstrap.min.css', '../../assets/vendor/font-awesome/css/all.css', '../../assets/css/header.css', '../../assets/css/bootstrapAccomodations.css', '../../assets/css/loginAndRegistration.css'],
    providers: [SignUpService, LoginService,
        {provide: NgbDateParserFormatter, useClass: DatepickerService}
    ]
})
export class SignUpComponent implements OnInit {

    // 0 -> no errors
    // 1 -> error when loading image
    // 2 -> error when logging in/out (unknown error)
    // 3 -> error with license ID or nickname, please use other ones
    error: number;
    isCompetitor: boolean;
    wasValidated: boolean;
    loading: boolean;
    signUpFormBirthdate: NgbDateStruct;
    signUpForm: SignUpForm;
    @ViewChild('form') signUpFormElement: ElementRef;

    constructor(private service: SignUpService,
                private loginService: LoginService,
                private router: Router,
                private activeRoute: ActivatedRoute,
                public genderService: GenderService,
                public beltService: BeltService,
                public imageService: ImageService,
                private datepickerService: NgbDateParserFormatter) {
        this.error = 0;
        this.isCompetitor = this.activeRoute.snapshot.params['role'] === 'competitor';
        this.wasValidated = false;
        this.signUpFormBirthdate = undefined;
        this.signUpForm = {
            birthDate: '',
            belt: '',
            dni: '',
            email: '',
            gender: '',
            gym: '',
            imageFile: '',
            licenseId: '',
            name: '',
            nickname: '',
            password: '',
            refereeRange: '',
            securityAnswer: '',
            securityQuestion: '',
            surname: '',
            weight: ''
        };
    }

    ngOnInit(): void {
    }

    signUp(event: Event): void{
        event.preventDefault();
        if (this.signUpFormElement.nativeElement.checkValidity()){
            this.loading = true;
            this.service.sendSignUp(this.signUpForm, (this.isCompetitor ? 'competitors' : 'referees')).subscribe(
                (success => {
                    this.loginService.login(this.signUpForm.nickname, this.signUpForm.password).subscribe(
                        (successfulLogin => {
                            this.imageService.onUpload('/api/' + (this.isCompetitor ? 'competitors' : 'referees') + '/' + this.signUpForm.licenseId + '/image').subscribe(
                                (successfulUpload => {
                                    this.loginService.logout().subscribe(
                                        (successfulLogout => {
                                            this.router.navigate(['login']);
                                        }),
                                        (wrongLogout => {
                                            this.error = 2;
                                            this.loading = false;
                                        })
                                    )
                                }), (error => {
                                    this.error = 1;
                                    this.loading = false;
                                })
                            )
                        }), (error => {
                            this.error = 2;
                            this.loading = false;
                        })
                    )
                }), (error => {
                    this.error = 3;
                    this.loading = false;
                })
            )
        } else {
            this.wasValidated = true;
        }
    }

    updateBirthdate(){
        this.signUpForm.birthDate = this.datepickerService.format(this.signUpFormBirthdate);
    }
}
