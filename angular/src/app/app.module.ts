import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule, HttpClientJsonpModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

// Firebase services + environment module
import { AngularFireModule } from "@angular/fire";
import { AngularFireAuthModule } from "@angular/fire/auth";
import { AngularFirestoreModule } from '@angular/fire/firestore';

import { environment } from "../environments/environment";

// Auth service
import { AuthService } from "./shared/services/auth.service";
import { TwitterService} from "./shared/services/twitter.service";

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";

// Gridster
import { GridsterModule} from "angular-gridster2";
import { MatTooltipModule } from "@angular/material/tooltip";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatTreeModule } from "@angular/material/tree";

import { SignInComponent } from './components/sign-in/sign-in.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { VerifyEmailComponent } from './components/verify-email/verify-email.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TwitterComponent } from './components/services/twitter/twitter.component';
import { SpotifyComponent } from './components/services/spotify/spotify.component';
import { SidebarComponent } from './components/dashboard/sidebar/sidebar.component';
import { TwitchComponent } from './components/services/twitch/twitch.component';
import {LoadingscreenComponent} from "./components/loadingscreen/loadingscreen.component";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import { TimelineComponent } from './components/services/twitter/timeline/timeline.component';
import {ScrollingModule} from "@angular/cdk/scrolling";
import { PostTweetComponent } from './components/services/twitter/post-tweet/post-tweet.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    SignInComponent,
    SignUpComponent,
    ForgotPasswordComponent,
    VerifyEmailComponent,
    DashboardComponent,
    SidebarComponent,
    TwitterComponent,
    SpotifyComponent,
    TwitchComponent,
    LoadingscreenComponent,
    TimelineComponent,
    PostTweetComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpClientJsonpModule,
    AppRoutingModule,
    AngularFireModule.initializeApp(environment.firebaseConfig),
    AngularFireAuthModule,
    AngularFirestoreModule,
    BrowserAnimationsModule,
    FontAwesomeModule,
    MatFormFieldModule,
    MatInputModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatTableModule,
    MatIconModule,
    GridsterModule,
    MatTooltipModule,
    MatSidenavModule,
    MatTreeModule,
    MatProgressSpinnerModule,
    ScrollingModule,
    FormsModule,
  ],
  providers: [
    AuthService,
    TwitterService,
  ],
  bootstrap: [AppComponent]
})

export class AppModule { }
