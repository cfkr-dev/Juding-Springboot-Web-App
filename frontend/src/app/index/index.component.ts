import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {PostInterface} from '../post/post.interface';

interface Email {
    name: string;
    email: string;
    subject: string;
    message: string;
    infoMessage: string;
}

interface EmailResponse{
    class: string;
    message: string;
}

@Component({
    selector: 'app-index',
    templateUrl: './index.component.html',
    styleUrls: ['../../assets/vendor/font-awesome/css/all.css', '../../assets/css/style.css', '../../assets/css/header.css', '../../assets/css/bootstrapAccomodations.css']
})
export class IndexComponent implements OnInit {

    emailValues: Email;
    emailResponse: EmailResponse;

    constructor(private http: HttpClient) {
        this.emailValues = {email: '', message: '', name: '', subject: '', infoMessage: ''};
        this.emailResponse = {class: '', message: ''};
    }

    page = 0;
    size = 0;
    postList: PostInterface[];

    ngOnInit(): void {
        // this.http.get('/api/posts?page=' + this.page + '&size=' + this.size).subscribe(
        //     response => {for (let post of response){ this.postList.concat(post); }}
        // );
    }

    email(event: Event): void {
        event.preventDefault();
        this.emailResponse = {class: 'info', message: 'Enviando...'};
        this.http.get('/api/index-email?name=' + this.emailValues.name + '&email=' + this.emailValues.email + '&subject=' + this.emailValues.subject + '&message=' + this.emailValues.message).subscribe(
            (response => this.emailResponse = {class: 'success', message: 'Correo enviado correctamente.'}),
            (error => this.emailResponse = {class: 'danger', message: 'Algo no funcionó bien. Inténtelo de nuevo.'})
        );
    }
}
