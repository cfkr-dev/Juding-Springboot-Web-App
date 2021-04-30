import {Component, OnInit} from '@angular/core';
import {Post} from '../../post.model';
import {PostsService} from '../../posts.service';
import {LoggedInUserService} from '../../../logged-in-user.service';
import {User} from '../../../user/user.model';
import {Router} from '@angular/router';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
    templateUrl: './post-list.html',
    styleUrls: ['../../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../../assets/vendor/aos/aos.css',
        '../../../../assets/vendor/font-awesome/css/all.css',
        '../../../../assets/css/style.css',
        '../../../../assets/css/header.css',
        '../../../../assets/css/bootstrapAccomodations.css',
        '../../../../assets/css/responsiveTable.css',
        '../../../../assets/css/adminScreen.css'],
    providers: [PostsService]
})
export class PostListComponent implements OnInit {
    posts: Post[];
    page: number;
    finalPage: boolean;
    user: User;
    loadedButton: boolean;
    idAlt: number;
    empty: boolean;
    loadedPage: boolean;

    constructor(private service: PostsService, private loggedInUser: LoggedInUserService, private router: Router, private modalService: NgbModal) {
        this.posts = [];
        this.page = 0;
        this.loadedButton = true;
        this.empty = true;
        this.loadedPage = false;
    }

    open(i: number, content): void {
        this.idAlt = this.posts[i].idPost;
        this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'lg'});
    }

    ngOnInit(): void {
        this.loggedInUser.getLoggedUser().subscribe(
            ((user: User) => {
                this.user = user;
                if (this.user.roles.includes('A')) {
                    this.service.getPosts(this.page).subscribe(
                        (response => {
                            this.posts = response.content;
                            (this.posts.length === 0) ? this.empty = true : this.empty = false;
                            this.finalPage = response.last;
                            this.loadedPage = true;
                        })
                    );
                }
            }),
            (error => {
                console.log(error);
                this.router.navigate(['/login']);
            })
        );
    }

    loadNextPage(): void {
        this.loadedButton = false;
        this.page = this.page + 1;
        this.service.getPosts(this.page).subscribe(
            (response => {
                this.posts = this.posts.concat(response.content);
                this.finalPage = response.last;
                this.loadedButton = true;
            })
        );
    }

    deletePost(): void {
        this.service.deletePost(this.idAlt).subscribe(
            (post => {
                const currentUrl = this.router.url;
                this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
                    this.router.navigate([currentUrl]);
                });
            })
        );
    }

}
