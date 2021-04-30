import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RecoveryForm} from "./password-recovery.component";

@Injectable()
export class PasswordRecoveryService {

    constructor(private httpClient: HttpClient) {
    }

    getSecQuestion(licenseId: string): Observable<string> {
        return this.httpClient.get('/api/passwordRecovery/?step=1&licenseId=' + licenseId, {responseType: 'text'}) as Observable<string>;
    }

    checkSecurityAnswer(licenseId: string, secAnswer: string): Observable<any> {
        return this.httpClient.head('/api/passwordRecovery/?step=2&licenseId=' + licenseId + '&secAnswer=' + secAnswer) as Observable<any>;
    }

    setNewPassword(data: RecoveryForm): Observable<any> {
        return this.httpClient.put('/api/passwordRecovery', {
            licenseId: data.licenseId,
            securityAnswer: data.secAnswer,
            newPassword: data.newPassword
        }) as Observable<any>;
    }
}
