import {RouterModule} from '@angular/router';
import {CompetitorListComponent} from './admin/user/components/competitor-list.component';
import {CompetitorEditComponent} from './admin/user/components/competitor-edit-component';
import {LoginComponent} from './login/login.component';
import {E403Component} from './error/e403/e403.component';
import {E404Component} from './error/e404/e404.component';
import {E500Component} from './error/e500/e500.component';

const appRoutes = [
    {path: 'competitors', component: CompetitorListComponent},
    {path: 'edit/:licenseId', component: CompetitorEditComponent},
    {path: 'login', component: LoginComponent},
    {path: '403', component: E403Component},
    {path: '404', component: E404Component},
    {path: '500', component: E500Component},
    {path: '', redirectTo: 'competitors', pathMatch: 'full'}
];

export const routing = RouterModule.forRoot(appRoutes);
