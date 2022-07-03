import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { NavbarComponent } from './components/navbar/navbar.component';
import { MainComponent } from './components/main/main.component';
import { ImagePostComponent } from './components/image-post/image-post.component';
import { TextPostComponent } from './components/text-post/text-post.component';
import { CreateCommunityComponent } from './components/create-community/create-community.component';
import { SinglePostComponent } from './components/single-post/single-post.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CommunityComponent } from './components/community/community.component';
import { CommentComponent } from './components/comment/comment.component';
import { ProfileInfoComponent } from './components/profile-info/profile-info.component';
import { CommunityInfoComponent } from './components/community-info/community-info.component';
import { CreatePostComponent } from './components/create-post/create-post.component';
import { AllReportsComponent } from './components/all-reports/all-reports.component';
import { CreateCommentComponent } from './components/create-comment/create-comment.component';
import { AllBannsComponent } from './components/all-banns/all-banns.component';
import { BannedPostComponent } from './components/banned-post/banned-post.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { ProfileEditComponent } from './components/profile-edit/profile-edit.component';
import { NewReportComponent } from './components/new-report/new-report.component';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { PostEditComponent } from './components/post-edit/post-edit.component';
import { CommunityEditComponent } from './components/community-edit/community-edit.component';
import { CreateRuleComponent } from './components/create-rule/create-rule.component';
import { CreateFlairComponent } from './components/create-flair/create-flair.component';
import { CustomValidatorDirective } from './directives/custom_validator.directive';
import { Interceptor } from 'src/app/services/interceptors/interceptor.service';
import { AngularFireModule } from "@angular/fire/compat";
import { AngularFireAuthModule } from "@angular/fire/compat/auth";
import { AngularFireStorageModule } from '@angular/fire/compat/storage';
import { AngularFirestoreModule } from '@angular/fire/compat/firestore';
import { AngularFireDatabaseModule } from '@angular/fire/compat/database';
import { environment } from '../environments/environment';
import { v4 as uuid } from 'uuid';
import { EditFlairComponent } from './components/edit-flair/edit-flair.component';
import { EditRuleComponent } from './components/edit-rule/edit-rule.component';
import { BlockUserComponent } from './components/block-user/block-user.component';
import { SuspendComponent } from './components/suspend/suspend.component';
import { ToastrModule } from 'ngx-toastr';
import { GuardsGuard } from './guards.guard';

// import { AngularFireStorageModule } from '@angular/fire/storage'
// import { AngularFireModule } from '@angular/fire'


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    MainComponent,
    ImagePostComponent,
    TextPostComponent,
    CreateCommunityComponent,
    SinglePostComponent,
    ProfileComponent,
    CommunityComponent,
    CommentComponent,
    ProfileInfoComponent,
    CommunityInfoComponent,
    CreatePostComponent,
    AllReportsComponent,
    CreateCommentComponent,
    AllBannsComponent,
    BannedPostComponent,
    ChangePasswordComponent,
    ProfileEditComponent,
    NewReportComponent,
    AdminPanelComponent,
    LoginComponent,
    RegistrationComponent,
    PostEditComponent,
    CommunityEditComponent,
    CreateRuleComponent,
    CreateFlairComponent,
    CustomValidatorDirective,
    EditFlairComponent,
    EditRuleComponent,
    BlockUserComponent,
    SuspendComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    MDBBootstrapModule.forRoot(),
    MatDialogModule,
    BrowserAnimationsModule,
    AngularFireModule.initializeApp(environment.firebase),
    AngularFireDatabaseModule,
    AngularFireStorageModule,
    ToastrModule.forRoot()
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true}, GuardsGuard], //{provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true}
  bootstrap: [AppComponent]
})
export class AppModule { }
