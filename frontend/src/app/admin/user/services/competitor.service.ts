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

    getCompetitor(id: number): Observable<User> {
        return this.httpClient.get(COMPETITORS_URL + '/' + id).pipe(
            catchError(error => this.handleError(error))
        ) as Observable<User>;
    }


    removeCompetitor(user: User) {
        return this.httpClient.delete(COMPETITORS_URL + '/' + user.licenseId).pipe(
            catchError(error => this.handleError(error))
        );
    }

    updateCompetitor(user: User) {
        return this.httpClient.put(COMPETITORS_URL + '/' + user.licenseId, user).pipe(
            catchError(error => this.handleError(error))
        );
    }

    private handleError(error: any) {
        console.log('ERROR:');
        console.error(error);
        return throwError('Server error (' + error.status + '): ' + error.text());
    }
}
