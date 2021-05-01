import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SignUpForm} from "./sign-up.component";
import {User} from "../user/user.model";
import {Observable} from "rxjs";

@Injectable()
export class SignUpService {

  constructor(private httpClient: HttpClient) { }

    sendSignUp(data: SignUpForm, role: string): Observable<User>{
      return this.httpClient.post('/api/' + role, data) as Observable<User>;
    }
}
