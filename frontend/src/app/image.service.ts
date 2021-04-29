import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ImageService {
    selectedFile: File;

    constructor(private http: HttpClient) {
    }

    onFileChanged(event): void {
        this.selectedFile = event.target.files[0];
    }

    getInterpretedFileName(): string {
        return (this.selectedFile) ? this.selectedFile.name : 'Examinar...';
    }

    onUpload(url: string): Observable<any> {
        const uploadData = new FormData();
        uploadData.append('file', this.selectedFile, this.selectedFile.name);
        return this.http.put(url, uploadData);
    }
}
