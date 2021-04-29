import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RankingInterface} from './RankingInterface';

@Injectable()
export class RankingService {

    constructor(private http: HttpClient) {
    }

    getUserImage(licenseId: string): Observable<Blob> {
        return this.http.get('/api/competitors/' + licenseId + '/image', {responseType: 'blob'}) as Observable<Blob>;
    }

    getRanking(): Observable<RankingInterface[]> {
        return this.http.get('/api/ranking', {withCredentials: true}) as Observable<RankingInterface[]>;
    }
}
