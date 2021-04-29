import {Component} from '@angular/core';
import {Post} from '../../post.model';
import {ActivatedRoute, Router} from '@angular/router';
import {PostsService} from '../../posts.service';
import {HttpClient} from '@angular/common/http';

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
export class PostFormComponent{
    post: Post;
    image: File = null;
    alert: boolean;

    constructor(private router: Router, activatedRoute: ActivatedRoute, private service: PostsService, private http: HttpClient) {
        this.alert = false;
        const id = activatedRoute.snapshot.params.id;
        if (id){
            service.getPost(id).subscribe(
                post => this.post = post,
                error => console.log(error)
            );
        }
        else {
            this.post = {author: undefined, title: '', body: '', timestamp: undefined};
        }
    }

    onImageSelected(event): void{
        this.image = event.target.files[0];
    }
    getInterpretedFileName(): string{
        return (this.image !== null) ? this.image.name : 'Examinar...';
    }

    submitForm(event: Event): void {
        event.preventDefault();
        this.service.savePost(this.post).subscribe(
            (post => {
                if (this.image !== null){
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
