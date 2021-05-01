import {Component, ElementRef, ViewChild} from '@angular/core';
import {PasswordRecoveryService} from "./password-recovery.service";
import {Router} from "@angular/router";

export interface RecoveryForm {
    licenseId: string;
    secQuestion: string;
    secAnswer: string;
    newPassword: string;
}

@Component({
    selector: 'app-password-recovery',
    templateUrl: './password-recovery.component.html',
    styleUrls: ['../../assets/vendor/font-awesome/css/all.css', '../../assets/vendor/bootstrap/css/bootstrap.min.css', '../../assets/css/header.css', '../../assets/css/bootstrapAccomodations.css', '../../assets/css/loginAndRegistration.css', '../../assets/css/securityQuestion.css'],
    providers: [PasswordRecoveryService]
})
export class PasswordRecoveryComponent {

    error: boolean;
    wasValidated: boolean;
    step: number;
    passwordType: boolean;
    recoveryForm: RecoveryForm;

    @ViewChild('form') form: ElementRef;

    constructor(private service: PasswordRecoveryService, private router: Router) {
        this.error = false;
        this.wasValidated = false;
        this.passwordType = true;
        this.step = 1;
        this.recoveryForm = {licenseId: "", newPassword: "", secAnswer: "", secQuestion: ""};
    }

    firstStep(event: Event): void {
        event.preventDefault();
        if (this.form.nativeElement.checkValidity()) {
            this.service.getSecQuestion(this.recoveryForm.licenseId).subscribe(
                ((secQuestion: string) => {
                    this.recoveryForm.secQuestion = secQuestion;
                    this.step = 2;
                }),
                (error => {
                    this.error = true;
                    this.wasValidated = false;
                    this.recoveryForm.licenseId = '';
                })
            )
        } else {
            this.wasValidated = true;
            this.error = false;
        }
    }

    secondStep(event: Event): void {
        event.preventDefault();
        if (this.form.nativeElement.checkValidity()) {
            this.service.checkSecurityAnswer(this.recoveryForm.licenseId, this.recoveryForm.secAnswer).subscribe(
                (success => {
                    this.step = 3;
                }),
                (error => {
                    this.error = true;
                    this.wasValidated = false;
                })
            )
        } else {
            this.wasValidated = true;
            this.error = false;
        }
    }

    thirdStep(event: Event): void {
        event.preventDefault();
        if (this.form.nativeElement.checkValidity()) {
            this.service.setNewPassword(this.recoveryForm).subscribe(
                (success => this.router.navigate(['login'])),
                (error => this.error = true)
            )
        } else {
            this.wasValidated = true;
            this.error = false;
        }
    }

    changeVisibility() {
        this.passwordType = !this.passwordType;
    }

}
