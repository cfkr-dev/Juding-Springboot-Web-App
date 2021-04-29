import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import {LoginComponent} from './login/login.component';
import { CompetitorListComponent } from './admin/user/components/competitor-list.component';
import { routing } from './app.routing';
import {HeaderComponent} from './header/header.component';
import { CompetitorEditComponent } from './admin/user/components/competitor-edit-component';
import { E403Component } from './error/e403/e403.component';
import { E404Component } from './error/e404/e404.component';
import { E500Component } from './error/e500/e500.component';

@NgModule({
  declarations: [AppComponent, HeaderComponent, CompetitorListComponent, LoginComponent, CompetitorEditComponent, E403Component, E404Component, E500Component],
  imports: [BrowserModule, FormsModule, HttpClientModule, routing],
  bootstrap: [AppComponent]
})
export class AppModule { }
