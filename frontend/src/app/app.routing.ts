import {RouterModule} from '@angular/router';
import {CompetitorListComponent} from './admin/user/components/competitor-list.component';
import {LoginComponent} from './login/login.component';

const appRoutes = [
    {path: 'competitors', component: CompetitorListComponent},
    {path: 'login', component: LoginComponent},
    {path: '', redirectTo: 'competitors', pathMatch: 'full'}
];

export const routing = RouterModule.forRoot(appRoutes);
