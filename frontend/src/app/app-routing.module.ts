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


const routes: Routes = [
  {path: '', component: IndexComponent},
  {path: 'cookiePolicy', component: CookiePolicyComponent},
  {path: 'termsAndConditionsOfUse', component: TermsAndConditionsOfUseComponent},
  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LoginComponent},
  {path: 'myHome', component: MyHomeComponent},
  {path: 'ranking', component: RankingComponent},
  {path: 'myProfile', component: MyProfileComponent},
  {path: 'myProfile/edit', component: MyProfileEditComponent},
  {path: 'competition/list', component: ListCompetitionComponent},
  {path: 'competition/:id', component: CompetitionDetailComponent}
];

const routerOptions: ExtraOptions = {
  useHash: false,
  scrollPositionRestoration: 'enabled',
  anchorScrolling: 'enabled'
};

@NgModule({
  imports: [RouterModule.forRoot(routes, routerOptions)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
