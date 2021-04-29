import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CompetitionDetailComponent} from './competition/competition-detail/competition-detail.component';
import {CompetitionFightTreeComponent} from './competition/competition-fight-tree/competition-fight-tree.component';
import {CompetitionControlComponent} from './competition/competition-control/competition-control.component';
import {LoginComponent} from './login/login.component';
import {FormsModule} from '@angular/forms';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {BrowserModule} from '@angular/platform-browser';
import {ListCompetitionComponent} from './competition/admin/list/list-competition.component';

@NgModule({
    declarations: [
        AppComponent,
        CompetitionDetailComponent,
        CompetitionFightTreeComponent,
        CompetitionControlComponent,
        LoginComponent,
        ListCompetitionComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        FormsModule
    ],
    providers: [],
    bootstrap: [AppComponent],
})

export class AppModule {
}
