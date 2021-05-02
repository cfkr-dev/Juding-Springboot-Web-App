import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Post} from './post.model';
import {Injectable} from '@angular/core';

const url = '/api/posts/';

@Injectable()
export class PostsService {
    constructor(private httpClient: HttpClient) {
    }

    getPost(id: number | string): Observable<Post> {
        return this.httpClient.get(url + id) as Observable<Post>;
    }

    getPosts(page: number): Observable<any> {
        return this.httpClient.get(url + '?page=' + page) as Observable<any>;
    }

    getRecentPosts(id: number | string): Observable<Post[]> {
        return this.httpClient.get(url + 'recent?dismissedPost=' + id) as Observable<Post[]>;
    }

    savePost(post: Post): Observable<Post> {
        if (post.idPost) {
            return this.httpClient.put(url + post.idPost, post, {withCredentials: true}) as Observable<Post>;
        } else {
            return this.httpClient.post(url, post, {withCredentials: true}) as Observable<Post>;
        }
    }

    deletePost(id: number | string): Observable<Post> {
        return this.httpClient.delete(url + id, {withCredentials: true}) as Observable<Post>;
    }
}
