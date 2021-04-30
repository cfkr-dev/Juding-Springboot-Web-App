import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../../../user/user.model';

const REFEREE_URL = 'api/referees';

@Injectable({providedIn: 'root'})
export class RefereeService {

    constructor(private httpClient: HttpClient) {
    }


    getReferees(page: number): Observable<any> {
        return this.httpClient.get(REFEREE_URL + '?page=' + page);
    }

    getReferee(id: number | string): Observable<any> {
        return this.httpClient.get(REFEREE_URL + '/' + id);
    }


    removeReferee(user: User): Observable<any> {
        return this.httpClient.delete(REFEREE_URL + '/' + user.licenseId);
    }

    updateReferee(user: User): Observable<any> {
        return this.httpClient.put(REFEREE_URL + '/' + user.licenseId, user);
    }

    getApplications(): Observable<any> {
        return this.httpClient.get(REFEREE_URL + '/applications');
    }

    admitReferee(user: User): Observable<any> {
        return this.httpClient.put(REFEREE_URL + '/applications/' + user.licenseId, {});
    }

}
