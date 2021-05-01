import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../user/user.model';

const BASE_URL = 'api/formValidation/users/alteration';

@Injectable({providedIn: 'root'})
export class FormValidationService {

    constructor(private httpClient: HttpClient) {
    }

    checkValidity(user: User): Observable<any> {
        return this.httpClient.get( BASE_URL + '?licenseId=' + user.licenseId + '&nickname=' + user.nickname);
    }
}
