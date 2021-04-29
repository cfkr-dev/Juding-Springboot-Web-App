import {LoginComponent} from './login/login.component';
import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {ListCompetitionComponent} from './competition/admin/list/list-competition.component';
import {CompetitionDetailComponent} from './competition/competition-detail/competition-detail.component';

const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'competition/list', component: ListCompetitionComponent},
    {path: 'competition/:id', component: CompetitionDetailComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
