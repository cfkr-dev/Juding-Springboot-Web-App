import {RouterModule} from '@angular/router';
import {CompetitorListComponent} from './admin/user/components/competitor-list.component';

const appRoutes = [
    {path: 'competitors', component: CompetitorListComponent},
    {path: '', redirectTo: 'competitors', pathMatch: 'full'}
];

export const routing = RouterModule.forRoot(appRoutes);
