import {Injectable} from "@angular/core";
import { HttpClient } from '@angular/common/http';
import {Observable, throwError} from "rxjs";
import {Competition} from "./competition.model";
import {catchError} from "rxjs/operators";
import {FightService} from "../fight/fight.service";

const BASE_URL = '/api/competition'

@Injectable()
export class CompetitionService {
    constructor(private httpClient: HttpClient) {}

    getCompetition(id: number | string): Observable<Competition> {
        return this.httpClient.get(BASE_URL + id).pipe(
            catchError(error => this.handleError(error))
        ) as Observable<Competition>;
    }

    updateCompetition(competition:Competition) {
        return this.httpClient.post(BASE_URL, competition).pipe(
            catchError(error => this.handleError(error))
        );
    }

    private handleError(error: any) {
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text())
    }

    getPeople(competition : Competition): number {
        return FightService.countParticipants(competition.fights);
    }
}
