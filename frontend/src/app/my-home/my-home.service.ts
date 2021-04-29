import {Injectable} from '@angular/core';
import {CompetitionInterface} from '../competition/competition.interface';
import {Observable} from 'rxjs';
import {UserInterface} from '../user/user.interface';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MyHomeService {

  constructor(private http: HttpClient) {
  }

  public getCompetitions(currentUser: UserInterface, urlParameter: string): Observable<CompetitionInterface[]> {
    return this.http.get('/api/competitions/' + currentUser.licenseId + '/' + urlParameter, {withCredentials: true}) as Observable<CompetitionInterface[]>;
  }

  public getCharts(currentUser: UserInterface): Observable<number[]> {
    return this.http.get('/api/competitors/points/' + currentUser.licenseId, {withCredentials: true}) as Observable<number[]>;
  }
}
