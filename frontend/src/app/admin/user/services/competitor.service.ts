import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, Subscription, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {User} from '../models/user.model';

const COMPETITORS_URL = 'api/competitors';

@Injectable({providedIn: 'root'})
export class CompetitorService {

    constructor(private httpClient: HttpClient) {
    }

    getCompetitors(page: number): Observable<any> {
        return this.httpClient.get(COMPETITORS_URL + '?page=' + page);
    }

    getCompetitor(id: number | string): Observable<any> {
        return this.httpClient.get(COMPETITORS_URL + '/' + id);
    }


    removeCompetitor(user: User): Observable<any> {
        return this.httpClient.delete(COMPETITORS_URL + '/' + user.licenseId);
    }

    updateCompetitor(user: User) {
        return this.httpClient.put(COMPETITORS_URL + '/' + user.licenseId, user);
    }
}
