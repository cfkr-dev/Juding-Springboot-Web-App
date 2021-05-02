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
import {AdminActivationService} from './security/admin-activation.service';
import {RefereeActivationService} from './security/referee-activation.service';
import {CompetitorActivationService} from './security/competitor-activation.service';
import {IsLoggedActivationService} from './security/isLogged-activation.service';
import {CompetitionControlComponent} from './competition/competition-control/competition-control.component';
import {AdminActivationService} from "./security/admin-activation.service";
import {RefereeActivationService} from "./security/referee-activation.service";
import {CompetitorActivationService} from "./security/competitor-activation.service";
import {IsLoggedActivationService} from "./security/isLogged-activation.service";
import {PasswordRecoveryComponent} from "./password-recovery/password-recovery.component";
import {SignUpComponent} from "./sign-up/sign-up.component";
import {CompetitionFormComponent} from './competition/admin/form/competition-form';


const routes: Routes = [
    {path: '', component: IndexComponent, data: {title: 'Inicio'}},
    {path: 'cookiePolicy', component: CookiePolicyComponent, data: {title: 'Política de cookies'}},
    {path: 'termsAndConditionsOfUse', component: TermsAndConditionsOfUseComponent, data: {title: 'Términos y condiciones de uso'}},
    {path: 'news/:id', component: PostDetailComponent, data: {title: 'Noticia'}},
    {path: 'passwordRecovery', component: PasswordRecoveryComponent, data: {title: 'Recuperación de contraseña'}},
    {path: 'signUp/:role', component: SignUpComponent, data: {title: 'Registro'}},

    // Logged in pages
    {path: 'login', component: LoginComponent, data: {title: 'Inicio de sesión'}},
    {path: 'logout', component: LoginComponent, canActivate: [IsLoggedActivationService], data: {title: 'Cierre de sesión'}},
    {path: 'myHome', component: MyHomeComponent, canActivate: [IsLoggedActivationService], data: {title: 'Mi pantalla de inicio'}},
    {path: 'ranking', component: RankingComponent, canActivate: [IsLoggedActivationService], data: {title: 'Ranking'}},
    {path: 'myProfile', component: MyProfileComponent, canActivate: [IsLoggedActivationService], data: {title: 'Mi perfil'}},
    {path: 'myProfile/edit', component: MyProfileEditComponent, canActivate: [IsLoggedActivationService], data: {title: 'Editar mi perfil'}},
    {path: 'competitions/:id', component: CompetitionDetailComponent, canActivate: [IsLoggedActivationService], data: {title: 'Competición'}},
      {path: 'competitions/:id/control', component: CompetitionControlComponent, canActivate: [IsLoggedActivationService], data: {title: 'Arbitraje de competición'}},

    // Admin pages
    {path: 'admin/competitions', component: ListCompetitionComponent, canActivate: [AdminActivationService], data: {title: 'Competiciones'}},
    {path: 'admin/posts', component: PostListComponent, canActivate: [AdminActivationService], data: {title: 'Noticias'}},
    {path: 'admin/posts/new', component: PostFormComponent, canActivate: [AdminActivationService], data: {title: 'Nueva noticia'}},
    {path: 'admin/posts/:id', component: PostFormComponent, canActivate: [AdminActivationService], data: {title: 'Edición de noticia'}},
    {path: 'admin/competitors', component: CompetitorListComponent, canActivate: [AdminActivationService], data: {title: 'Competidores'}},
    {
        path: 'admin/competitors/edit/:licenseId',
        component: CompetitorEditComponent,
        canActivate: [AdminActivationService], data: {title: 'Edición de competidor'}
    },
    {path: 'admin/referees', component: RefereeListComponent, canActivate: [AdminActivationService], data: {title: 'Árbitros'}},
    {path: 'admin/referees/edit/:licenseId', component: RefereeEditComponent, canActivate: [AdminActivationService], data: {title: 'Edición de árbitro'}},
    {path: 'admin/competition/new', component: CompetitionFormComponent, canActivate: [AdminActivationService], data: {title:  'Nueva competición'}},
    {path: 'admin/competition/edit/:id', component: CompetitionFormComponent, canActivate: [AdminActivationService], data: {title: 'Editar competición'}},
    // Error pages
    {path: '403', component: E403Component, data: {title: 'Error 403'}},
    {path: '500', component: E500Component, data: {title: 'Error 500'}},
    {path: '**', component: E404Component, data: {title: 'Error 404'}}
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
