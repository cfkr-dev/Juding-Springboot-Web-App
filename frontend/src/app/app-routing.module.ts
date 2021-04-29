import {NgModule} from '@angular/core';
import {ExtraOptions, RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {MyHomeComponent} from './loggedInUserPages/my-home/my-home.component';
import {IndexComponent} from './mainPages/index/index.component';
import {RankingComponent} from './loggedInUserPages/ranking/ranking.component';
import {CookiePolicyComponent} from './mainPages/cookie-policy/cookie-policy.component';
import {TermsAndConditionsOfUseComponent} from './mainPages/terms-and-conditions-of-use/terms-and-conditions-of-use.component';
import {MyProfileComponent} from './loggedInUserPages/my-profile/my-profile.component';
import {MyProfileEditComponent} from './loggedInUserPages/my-profile-edit/my-profile-edit.component';

const routes: Routes = [
  {path: '', component: IndexComponent},
  {path: 'cookiePolicy', component: CookiePolicyComponent},
  {path: 'termsAndConditionsOfUse', component: TermsAndConditionsOfUseComponent},
  {path: 'login', component: LoginComponent},
  {path: 'logout', component: LoginComponent},
  {path: 'myHome', component: MyHomeComponent},
  {path: 'ranking', component: RankingComponent},
  {path: 'myProfile', component: MyProfileComponent},
  {path: 'myProfile/edit', component: MyProfileEditComponent}
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
