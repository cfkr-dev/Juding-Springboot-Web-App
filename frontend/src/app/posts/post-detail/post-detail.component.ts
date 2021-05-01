import {Component} from '@angular/core';
import {Post} from '../post.model';
import {PostsService} from '../posts.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
    templateUrl: './post-detail.html',
    styleUrls: ['../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../assets/css/style.css',
        '../../../assets/css/header.css',
        '../../../assets/css/responsiveTable.css',
        '../../../assets/css/news.css'],
    providers: [PostsService]
})
export class PostDetailComponent {
    loadedPage: boolean;
    post: Post;
    postList: Post[];

    constructor(public postService: PostsService, private router: Router, activatedRoute: ActivatedRoute) {
        this.loadedPage = false;
        const id = activatedRoute.snapshot.params.id;
        postService.getPost(id).subscribe(
            ((post: Post) => {
                this.post = post;
                postService.getRecentPosts(this.post.idPost).subscribe(
                    ((postList) => {
                        this.postList = postList;
                        this.loadedPage = true;
                    })
                );
            }),
            error => this.router.navigate(['/404'])
        );
    }

    goToNews(newsId: number): void {
        const currentUrl = '/news/' + newsId;
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
            this.router.navigate([currentUrl]);
        });
    }
}

