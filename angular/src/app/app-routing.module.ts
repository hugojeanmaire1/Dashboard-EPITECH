import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Import all the components for which navigation service has to be activated
import { SignInComponent} from "./components/sign-in/sign-in.component";
import { SignUpComponent} from "./components/sign-up/sign-up.component";
import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { ForgotPasswordComponent } from "./components/forgot-password/forgot-password.component";
import { VerifyEmailComponent } from "./components/verify-email/verify-email.component";

import {AuthGuard} from "./shared/guard/auth.guard";
import {TwitterGuard} from "./shared/guard/twitter.guard";
import {LoadingscreenComponent} from "./components/loadingscreen/loadingscreen.component";

const routes: Routes = [
  { path: '', redirectTo: '/sign-in', pathMatch: 'full'},
  { path: 'sign-in', component: SignInComponent },
  { path: 'register-user', component: SignUpComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'verify-email-address', component: VerifyEmailComponent },
  { path: 'login/twitter/callback', canActivate: [TwitterGuard], component: LoadingscreenComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
