import { Injectable, NgZone } from '@angular/core';
import { User } from "./user";
import firebase from 'firebase/app';
import "firebase/auth";
import 'firebase/firestore';
import { AngularFirestore, AngularFirestoreDocument } from '@angular/fire/firestore';
import { Router } from "@angular/router";
import { Store } from '@ngrx/store';
import { UserActionTypes } from "../../store/actions/store.actions";
import { LoginComplete, Login, LoginError} from "../../store/actions/store.actions";

import { HttpClient } from "@angular/common/http";
import {reduce} from "rxjs/operators";
import {reducer} from "../../store/reducers/store.reducer";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  userData: any; // Save logged in user data

  constructor(
    public afs: AngularFirestore,     // Inject Firestore service
    public router: Router,
    public ngZone: NgZone,             // NgZone service to remove outside scope warning
    private _httpClient: HttpClient,
    private store: Store<any>,
    ) {
      firebase.auth().onAuthStateChanged(function (user) {
        if (user) {
          this.userData = user;
          localStorage.setItem('user', JSON.stringify(this.userData));
          JSON.parse(localStorage.getItem('user'));
        } else {
          localStorage.setItem('user', null);
          JSON.parse(localStorage.getItem('user'));
        }
      })
  }

  // Sign in with email/password
  SignIn(email, password) {
    return firebase.auth().signInWithEmailAndPassword(email, password)
      .then((result) => {
          this.ngZone.run(() => {
            this.router.navigate(['dashboard'])
          });
          this.SetUserData(result.user);
          this._httpClient.get("http://localhost:8080/checkuser/" + result.user.uid)
            .subscribe(response => {
              console.log("Result = ", response);
              this.store.dispatch(new LoginComplete(response));
            });
      }).catch((error) => {
        console.log(error)
        window.alert(error.message);
      })
  }

  // Sign up with email/password
  SignUp(email, password) {
    return firebase.auth().createUserWithEmailAndPassword(email, password)
      .then((result) => {
        /* Call the SendVerificaitonMail() function when new user sign
        up and returns promise */
        this.SendVerificationMail();
        this.SetUserData(result.user);
        this._httpClient.get("http://localhost:8080/create-user/" + result.user.uid)
          .subscribe(response => {
            console.log("User Created !", response)
          })
      }).catch((error) => {
        window.alert(error.message)
      })
  }

  //Send email verfificaiton when new user sign up
  SendVerificationMail() {
    return firebase.auth().currentUser.sendEmailVerification()
      .then(() => {
        this.router.navigate(['verify-email-address']);
      })
  }

  // Reset Forggot password
  ForgotPassword(passwordResetEmail) {
    return firebase.auth().sendPasswordResetEmail(passwordResetEmail)
      .then(() => {
        window.alert('Password reset email sent, check your inbox.');
      }).catch((error) => {
        window.alert(error)
      })
  }

  // Returns true when user is looged in and email is verified
  get isLoggedIn(): boolean {
    const user = JSON.parse(localStorage.getItem('user'));
    return (user !== null);
  }

  // Sign in with Google
  GoogleAuth() {
    return this.AuthLogin(new firebase.auth.GoogleAuthProvider());
  }

  // Sign in with GitHub
  GithubAuth() {
    return this.AuthLogin(new firebase.auth.GithubAuthProvider());
  }

  TwitterAuth() {
    return this.AuthLogin(new firebase.auth.TwitterAuthProvider());
  }

  MicrosoftAuth() {
    return this.AuthLogin(new firebase.auth.OAuthProvider('microsoft.com'));
  }

  // Auth logic to run auth providers
  AuthLogin(provider) {
    return firebase.auth().signInWithPopup(provider)
      .then((result) => {
        this.ngZone.run(() => {
          this._httpClient.get("http://localhost:8080/checkuser/" + result.user.uid)
            .subscribe(response => {
              console.log("Response = ", response);
              this.store.dispatch(new LoginComplete(response));
              this.SetUserData(response);
            });
          this.router.navigate(['dashboard']);
        })
      }).catch((error) => {
        window.alert(error)
      })
  }

  /* Setting up user data when sign in with username/password,
  sign up with username/password and sign in with social auth
  provider in Firestore database using AngularFirestore + AngularFirestoreDocument service */
  SetUserData(user) {
    const userRef: AngularFirestoreDocument<any> = this.afs.doc(`users/${user.uid}`);
    const userData: User = {
      uid: user.uid,
      email: user.email,
      displayName: user.displayName,
      photoUrl: user.photoURL,
      emailVerified: user.emailVerified,
      services: user.services,
      widgets: user.widgets,
    }
    return userRef.set(userData, {
      merge: true
    })
  }

  getUserData() {
    console.log(this.userData);
    return this.userData;
  }

  // Sign out
  SignOut() {
    return firebase.auth().signOut().then(() => {
      localStorage.removeItem('user');
      this.router.navigate(['sign-in']);
    })
  }
}
