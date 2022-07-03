import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { ImagePostComponent } from './components/image-post/image-post.component';
import { MainComponent } from './components/main/main.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { SinglePostComponent } from './components/single-post/single-post.component';
import { CommunityComponent } from './components/community/community.component';
import { CreateCommunityComponent } from './components/create-community/create-community.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { CreateCommentComponent } from './components/create-comment/create-comment.component';
import { AllBannsComponent } from './components/all-banns/all-banns.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { ProfileEditComponent } from './components/profile-edit/profile-edit.component';
import { NewReportComponent } from './components/new-report/new-report.component';
import { PostEditComponent } from './components/post-edit/post-edit.component';
import { CommunityEditComponent } from './components/community-edit/community-edit.component';
import { CreateFlairComponent } from './components/create-flair/create-flair.component';
import { CreateRuleComponent } from './components/create-rule/create-rule.component';
import { GuardsGuard } from './guards.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'home', component: MainComponent },
  { path: 'register', component: RegistrationComponent },
  { path: 'post/:id', component: SinglePostComponent },
  { path: 'community/:id', component: CommunityComponent },
  { path: 'create-community', component: CreateCommunityComponent },
  { path: 'profile/:id', component: ProfileComponent },
  { path: 'create-post', component: CreatePostComponent },
  { path: 'create-comment', component: CreateCommentComponent },
  { path: 'all-banns', component: AllBannsComponent, canActivate: [GuardsGuard]}, //, canActivate: [GuardsGuard]
  { path: 'reset-password/:id', component: ChangePasswordComponent },
  { path: 'edit-profile-info/:id', component: ProfileEditComponent },
  { path: 'new-report', component: NewReportComponent },
  { path: 'post-edit/:id', component: PostEditComponent },
  { path: 'community-edit/:id', component: CommunityEditComponent },
  { path: 'create-rule', component: CreateRuleComponent },
  { path: 'create-flair', component: CreateFlairComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
