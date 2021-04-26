import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import {LoginComponent} from './login/login.component';
import { CompetitorListComponent } from './admin/user/components/competitor-list.component';
import { routing } from './app.routing';
import {HeaderComponent} from './header/header.component';

@NgModule({
  declarations: [AppComponent, HeaderComponent, CompetitorListComponent, LoginComponent],
  imports: [BrowserModule, FormsModule, HttpClientModule, routing],
  bootstrap: [AppComponent]
})
export class AppModule { }
