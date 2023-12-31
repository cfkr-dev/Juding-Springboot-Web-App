import {Competition} from './competition.model';
import {FightService} from '../fight/fight.service';
import {Fight} from '../fight/fight.model';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';

const BASE_URL = '/api/competitions/';

@Injectable()
export class CompetitionService {
    constructor(private httpClient: HttpClient) {
    }

    getCompetition(id: number | string): Observable<Competition> {
        return this.httpClient.get(BASE_URL + id, {withCredentials: true}) as Observable<Competition>;
    }

    getCompetitionPage(page: number): Observable<any> {
        return this.httpClient.get(BASE_URL + '?page=' + page, {withCredentials: true}) as Observable<any>;
    }

    saveCompetition(competition: any): Observable<any> {
        if (competition.idCompetition) {
            return this.httpClient.put(BASE_URL + competition.idCompetition, competition, {withCredentials: true}) as Observable<any>;
        } else {
            return this.httpClient.post(BASE_URL, competition, {withCredentials: true}) as Observable<any>;
        }
    }

    getPeople(competition: Competition): number {
        return FightService.countParticipants(competition.fights);
    }

    getFight(competition: Competition, numberOfFight: number): Fight {
        return competition.fights[numberOfFight];
    }

    deleteCompetition(id: number): Observable<any> {
        return this.httpClient.delete(BASE_URL + id, {withCredentials: true}) as Observable<any>;
    }

    translatingDates(competition: Competition): string {
        const localDate: Date = new Date();
        if (localDate > competition.startDate) {
            if (localDate > competition.endDate) {
                return 'Finalizada';
            } else {
                return 'Comenzada';
            }
        } else {
            return 'Por comenzar';
        }
    }

    haveCompetitions(competitions: Competition[]): boolean {
        return (competitions == null);
    }

    private handleError(error: any): any {
        console.error(error);
        return throwError('Server error (' + error.status + '): ' + error.text());
    }

    updateFight(idCompetition: number, winner: string, loser: string): Observable<Competition> {
        return this.httpClient.put(BASE_URL + idCompetition + '/control', {idCompetition: idCompetition, winner: winner, loser: loser}, {withCredentials: true}) as Observable<Competition>;
    }
}
