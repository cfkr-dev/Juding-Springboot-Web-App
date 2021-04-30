import {NgModule} from '@angular/core';
import {ExtraOptions, RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {MyHomeComponent} from './my-home/my-home.component';
import {IndexComponent} from './index/index.component';
import {RankingComponent} from './ranking/ranking.component';
import {CookiePolicyComponent} from './cookie-policy/cookie-policy.component';
import {TermsAndConditionsOfUseComponent} from './terms-and-conditions-of-use/terms-and-conditions-of-use.component';
import {MyProfileComponent} from './my-profile/my-profile.component';
import {MyProfileEditComponent} from './my-profile-edit/my-profile-edit.component';
import {ListCompetitionComponent} from './competition/admin/list/list-competition.component';
import {CompetitionDetailComponent} from './competition/competition-detail/competition-detail.component';
import {PostListComponent} from './posts/admin/post-list/post-list.component';
import {PostDetailComponent} from './posts/post-detail/post-detail.component';
import {PostFormComponent} from './posts/admin/post-forms/post-form.component';
import {CompetitorListComponent} from './admin/user/components/competitor-list.component';
import {CompetitorEditComponent} from './admin/user/components/competitor-edit-component';
import {RefereeListComponent} from './admin/user/components/referee-list.component';
import {RefereeEditComponent} from './admin/user/components/referee-edit.component';
import {E403Component} from './error/e403/e403.component';
import {E404Component} from './error/e404/e404.component';
import {E500Component} from './error/e500/e500.component';
import {AdminActivationService} from "./security/admin-activation.service";
import {RefereeActivationService} from "./security/referee-activation.service";
import {CompetitorActivationService} from "./security/competitor-activation.service";
import {IsLoggedActivationService} from "./security/isLogged-activation.service";
import {PasswordRecoveryComponent} from "./password-recovery/password-recovery.component";


const routes: Routes = [
    {path: '', component: IndexComponent},
    {path: 'cookiePolicy', component: CookiePolicyComponent},
    {path: 'termsAndConditionsOfUse', component: TermsAndConditionsOfUseComponent},
    {path: 'news/:id', component: PostDetailComponent},
    {path: 'passwordRecovery', component: PasswordRecoveryComponent},

    // Logged in pages
    {path: 'login', component: LoginComponent},
    {path: 'logout', component: LoginComponent, canActivate: [IsLoggedActivationService]},
    {path: 'myHome', component: MyHomeComponent, canActivate: [IsLoggedActivationService]},
    {path: 'ranking', component: RankingComponent, canActivate: [IsLoggedActivationService]},
    {path: 'myProfile', component: MyProfileComponent, canActivate: [IsLoggedActivationService]},
    {path: 'myProfile/edit', component: MyProfileEditComponent, canActivate: [IsLoggedActivationService]},
    {path: 'competitions/:id', component: CompetitionDetailComponent, canActivate: [IsLoggedActivationService]},

    // Admin pages
    {path: 'admin/competitions', component: ListCompetitionComponent, canActivate: [AdminActivationService]},
    {path: 'admin/posts', component: PostListComponent, canActivate: [AdminActivationService]},
    {path: 'admin/posts/new', component: PostFormComponent, canActivate: [AdminActivationService]},
    {path: 'admin/posts/:id', component: PostFormComponent, canActivate: [AdminActivationService]},
    {path: 'admin/competitors', component: CompetitorListComponent, canActivate: [AdminActivationService]},
    {
        path: 'admin/competitors/edit/:licenseId',
        component: CompetitorEditComponent,
        canActivate: [AdminActivationService]
    },
    {path: 'admin/referees', component: RefereeListComponent, canActivate: [AdminActivationService]},
    {path: 'admin/referees/edit/:licenseId', component: RefereeEditComponent, canActivate: [AdminActivationService]},

    // Error pages
    {path: '403', component: E403Component},
    {path: '500', component: E500Component},
    {path: '**', component: E404Component}
];

const routerOptions: ExtraOptions = {
    useHash: false,
    scrollPositionRestoration: 'enabled',
    anchorScrolling: 'enabled'
};

@NgModule({
    imports: [RouterModule.forRoot(routes, routerOptions)],
    exports: [RouterModule],
    providers: [AdminActivationService, RefereeActivationService, CompetitorActivationService, IsLoggedActivationService]
})
export class AppRoutingModule {
}
