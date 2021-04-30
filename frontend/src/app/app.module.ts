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
import {MyProfileEditComponent} from './my-profile-edit/my-profile-edit.component';
import {CompetitionDetailComponent} from './competition/competition-detail/competition-detail.component';
import {CompetitionFightTreeComponent} from './competition/competition-fight-tree/competition-fight-tree.component';
import {CompetitionControlComponent} from './competition/competition-control/competition-control.component';
import {ListCompetitionComponent} from './competition/admin/list/list-competition.component';
import {PostDetailComponent} from './posts/post-detail/post-detail.component';
import {PostFormComponent} from './posts/admin/post-forms/post-form.component';
import {PostListComponent} from './posts/admin/post-list/post-list.component';
import {CompetitorListComponent} from './admin/user/components/competitor-list.component';
import {CompetitorEditComponent} from './admin/user/components/competitor-edit-component';
import {E403Component} from './error/e403/e403.component';
import {E500Component} from './error/e500/e500.component';
import {RefereeEditComponent} from './admin/user/components/referee-edit.component';
import {E404Component} from './error/e404/e404.component';
import {RefereeListComponent} from './admin/user/components/referee-list.component';
import {PasswordRecoveryComponent} from './password-recovery/password-recovery.component';
import { SignUpComponent } from './sign-up/sign-up.component';


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
        MyProfileEditComponent,
        CompetitionDetailComponent,
        CompetitionFightTreeComponent,
        CompetitionControlComponent,
        ListCompetitionComponent,
        PostListComponent,
        PostDetailComponent,
        PostFormComponent,
        AppComponent,
        HeaderComponent,
        CompetitorListComponent,
        CompetitorEditComponent,
        E403Component,
        E404Component,
        E500Component,
        RefereeListComponent,
        RefereeEditComponent,
        PasswordRecoveryComponent,
        SignUpComponent
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
