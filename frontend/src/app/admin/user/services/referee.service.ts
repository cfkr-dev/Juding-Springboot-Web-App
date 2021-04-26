import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

import {User} from '../models/user.model';

const REFEREE_URL = 'api/referees';

@Injectable({providedIn: 'root'})
export class RefereeService {

  constructor(private httpClient: HttpClient) {
  }

  getReferees(page: number): Observable<User[]> {
    return this.httpClient.get(REFEREE_URL + '?page=' + page).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<User[]>;
  }

  getApplications(): Observable<User[]> {
    return this.httpClient.get(REFEREE_URL + '/applications').pipe(
      catchError(error => this.handleError(error))
    ) as Observable<User[]>;
  }

  getReferee(id: number): Observable<User> {
    return this.httpClient.get(REFEREE_URL + '/' + id).pipe(
      catchError(error => this.handleError(error))
    ) as Observable<User>;
  }


  removeReferee(user: User) {
    return this.httpClient.delete(REFEREE_URL + '/' + user.licenseId).pipe(
      catchError(error => this.handleError(error))
    );
  }

  updateReferee(user: User) {
    return this.httpClient.put(REFEREE_URL + '/' + user.licenseId, user).pipe(
      catchError(error => this.handleError(error))
    );
  }

  admitReferee(user: User) {
    return this.httpClient.put(REFEREE_URL + '/applications/' + user.licenseId, user).pipe(
      catchError(error => this.handleError(error))
    );
  }

  private handleError(error: any) {
    console.log('ERROR:');
    console.error(error);
    return throwError('Server error (' + error.status + '): ' + error.text());
  }
}
