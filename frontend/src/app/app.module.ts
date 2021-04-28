import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {MyHomeComponent} from './my-home/my-home.component';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {ChartsModule} from 'ng2-charts';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HeaderComponent} from './header/header.component';
import {IndexComponent} from './index/index.component';
import {RankingComponent} from './ranking/ranking.component';
import {MyProfileComponent} from './my-profile/my-profile.component';
import {TermsAndConditionsOfUseComponent} from './terms-and-conditions-of-use/terms-and-conditions-of-use.component';
import {CookiePolicyComponent} from './cookie-policy/cookie-policy.component';
import { MyProfileEditComponent } from './my-profile-edit/my-profile-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MyHomeComponent,
    HeaderComponent,
    IndexComponent,
    RankingComponent,
    MyProfileComponent,
    TermsAndConditionsOfUseComponent,
    CookiePolicyComponent,
    MyProfileEditComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ChartsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
