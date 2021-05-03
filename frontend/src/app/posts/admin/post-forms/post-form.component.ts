import {Component} from '@angular/core';
import {Post} from '../../post.model';
import {ActivatedRoute, Router} from '@angular/router';
import {PostsService} from '../../posts.service';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../../../environments/environment";

@Component({
    templateUrl: './post-form.html',
    styleUrls: ['../../../../assets/vendor/bootstrap/v4/css/bootstrap.css',
        '../../../../assets/vendor/datepicker/jquery.datetimepicker.min.css',
        '../../../../assets/vendor/font-awesome/css/all.css',
        '../../../../assets/css/style.css',
        '../../../../assets/css/header.css',
        '../../../../assets/css/bootstrapAccomodations.css',
        '../../../../assets/css/post.css',
        '../../../../assets/css/loginAndRegistration.css'],
    providers: [PostsService]
})
export class PostFormComponent {
    loadedPage: boolean;
    post: Post;
    image: File = null;
    alert: boolean;
    validation: string;
    environment: any;


    constructor(private router: Router, activatedRoute: ActivatedRoute, private service: PostsService, private http: HttpClient) {
        this.environment = environment;
        this.validation = 'needs-validation';
        this.loadedPage = false;
        this.alert = false;
        const id = activatedRoute.snapshot.params.id;
        if (id) {
            service.getPost(id).subscribe(
                post => {
                    this.post = post;
                    this.loadedPage = true;
                },
                error => this.router.navigate(['/404'])
            )
            ;
        } else {
            this.post = {author: undefined, title: '', body: '', timestamp: undefined};
            this.loadedPage = true;
        }
    }

    onImageSelected(event): void {
        this.image = event.target.files[0];
    }

    getInterpretedFileName(): string {
        return (this.image !== null) ? this.image.name : 'Examinar...';
    }

    submitForm(event: Event): void {
        event.preventDefault();
        this.validation = 'was-validated';
        this.service.savePost(this.post).subscribe(
            (post => {
                if (this.image !== null) {
                    const formData = new FormData();
                    formData.append('file', this.image, this.image.name);
                    this.http.put('/api/posts/' + post.idPost + '/image', formData, {withCredentials: true}).subscribe(
                        (response => {
                            this.router.navigate(['/admin/posts']);
                        })
                    );
                }
                this.router.navigate(['/admin/posts']);
            }),
            (error => {
                this.alert = true;
            })
        );
    }
}
