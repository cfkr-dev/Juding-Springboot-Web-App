import {Injectable} from '@angular/core';
import {CompetitionInterface} from '../competition/competition.interface';
import {Observable} from 'rxjs';
import {User} from '../user/user.model';
import {HttpClient} from '@angular/common/http';
import {Competition} from "../competition/competition.model";

@Injectable()
export class MyHomeService {

    constructor(private http: HttpClient) {
    }

    public getCompetitions(currentUser: User, urlParameter: string): Observable<CompetitionInterface[]> {
        return this.http.get('/api/competitions/' + currentUser.licenseId + '/' + urlParameter, {withCredentials: true}) as Observable<CompetitionInterface[]>;
    }

    public joinCompetition(idCompetition: string): Observable<Competition>{
        return this.http.put('/api/competitions/members/' + idCompetition, {}) as Observable<Competition>;
    }

    public getCharts(currentUser: User): Observable<number[]> {
        return this.http.get('/api/competitors/points/' + currentUser.licenseId, {withCredentials: true}) as Observable<number[]>;
    }
}
