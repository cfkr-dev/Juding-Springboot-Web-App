import {Component, OnInit} from '@angular/core';
import {PostInterface} from '../post/post.interface';
import {IndexService} from './index.service';

interface Email {
    name: string;
    email: string;
    subject: string;
    message: string;
}

interface Posts {
    list: PostInterface[];
    hasNext: boolean;
    isLoading: boolean;
    page: number;
}

@Component({
    selector: 'app-index',
    templateUrl: './index.component.html',
    styleUrls: ['../../assets/vendor/font-awesome/css/all.css', '../../assets/css/style.css', '../../assets/css/header.css', '../../assets/css/bootstrapAccomodations.css'],
    providers: [IndexService]
})
export class IndexComponent implements OnInit {

    emailValues: Email;
    // 0 -> invisible
    // 1 -> sending
    // 2 -> success
    // 3 -> error
    emailResponse: number;
    posts: Posts;

    constructor(private indexService: IndexService) {
        this.emailValues = {email: '', message: '', name: '', subject: ''};
        this.emailResponse = 0;
        this.posts = {hasNext: true, isLoading: false, list: [], page: 0};
    }

    ngOnInit(): void {
        this.getMorePosts();
    }

    getMorePosts(): void {
        this.posts.isLoading = true;
        this.indexService.getPosts(this.posts.page).subscribe(
            (response) => {
                response.content.map(post => this.posts.list.push(post));
                this.posts.hasNext = !response.last;
                this.posts.isLoading = false;
                this.posts.page += 1;
            }
        );
    }

    email(event: Event): void {
        event.preventDefault();
        this.emailResponse = 1;
        this.indexService.sendEmail(this.emailValues).subscribe(
            (response => this.emailResponse = 2),
            (error => this.emailResponse = 3)
        );
    }
}
