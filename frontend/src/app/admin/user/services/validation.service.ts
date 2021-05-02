import {Injectable} from '@angular/core';
import {User} from "../../../user/user.model";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class ValidationService {

    constructor(private httpClient: HttpClient) {
    }

    checkNickname(user: User): Observable<any> {
        return this.httpClient.get('/api/formValidation/users/alteration?licenseId=' + user.licenseId + '&nickname=' + user.nickname);
    }
}
