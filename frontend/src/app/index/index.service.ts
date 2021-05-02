import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PostInterface} from '../posts/post.interface';

@Injectable()
export class IndexService {

    constructor(private http: HttpClient) {
    }

    sendEmail(emailValues): Observable<any> {
        return this.http.get('/api/index-email?name=' + emailValues.name + '&email=' + emailValues.email + '&subject=' + emailValues.subject + '&message=' + emailValues.message);
    }

    getPosts(page: number): Observable<any> {
        return this.http.get('/api/posts?page=' + page + '&size=3') as Observable<PostInterface[]>;
    }
}
