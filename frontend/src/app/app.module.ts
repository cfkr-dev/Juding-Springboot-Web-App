import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CompetitionDetail} from './competition/competition-detail/competition-detail.component';
import {CompetitionFightTreeComponent} from './competition/competition-fight-tree/competitionFightTree.component';
import {HttpClientModule} from '@angular/common/http';
import {CompetitionControlComponent} from './competition/competition-control/competition-control.component';


@NgModule({
    declarations: [
        AppComponent,
        CompetitionDetail,
        CompetitionFightTreeComponent,
        CompetitionControlComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
