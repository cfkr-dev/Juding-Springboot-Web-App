import {Injectable} from '@angular/core';
import {CompetitionInterface} from '../competition/competition.interface';
import {Observable} from 'rxjs';
import {User} from '../user/user.model';
import {HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class MyHomeService {

    constructor(private http: HttpClient) {
    }

    public getCompetitions(currentUser: User, urlParameter: string): Observable<CompetitionInterface[]> {
        return this.http.get('/api/competitions/' + currentUser.licenseId + '/' + urlParameter, {withCredentials: true}) as Observable<CompetitionInterface[]>;
    }

    public getCharts(currentUser: User): Observable<number[]> {
        return this.http.get('/api/competitors/points/' + currentUser.licenseId, {withCredentials: true}) as Observable<number[]>;
    }
}
