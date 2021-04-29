import { Injectable } from '@angular/core';
import {UserInterface} from '../user/user.interface';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {ProfileEditForm} from './my-profile-edit.component';

@Injectable()
export class MyProfileEditService {

  constructor(private http: HttpClient) { }

  public sendData(userData: ProfileEditForm, role: string): Observable<UserInterface>{
    return this.http.put('/api/' + role + '/' + userData.licenseId, userData, {withCredentials: true}) as Observable<UserInterface>;
  }
}
