import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PostListComponent} from './posts/admin/post-list/post-list.component';
import {PostDetailComponent} from './posts/post-detail/post-detail.component';
import {PostFormComponent} from './posts/admin/post-forms/post-form.component';
import {LoginComponent} from './login/login.component';

const routes: Routes = [
    {path: 'admin/post/list', component: PostListComponent},
    {path: 'news/:id', component: PostDetailComponent},
    {path: 'admin/post/new', component: PostFormComponent},
    {path: 'admin/post/edit/:id', component: PostFormComponent},
    {path: 'login', component: LoginComponent}
];

@NgModule({
    imports: [RouterModule.forRoot(routes,{onSameUrlNavigation: 'reload'})],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
