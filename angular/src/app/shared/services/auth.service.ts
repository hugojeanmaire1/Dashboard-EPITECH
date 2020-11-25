import { Injectable, NgZone } from '@angular/core';
import firebase from 'firebase/app';
import "firebase/auth";
import 'firebase/firestore';
import { AngularFirestore } from '@angular/fire/firestore';
import { Router } from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': "application/json",
  })
};

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
          this._httpClient.post("http://localhost:8080/users/check-user", result.user, httpOptions)
            .subscribe(response => {
              console.log("Response SignIn = ", response)
              this.ngZone.run(() => {
                this.router.navigate(['dashboard'])
              });
            })
      }).catch((error) => {
        console.log(error)
        window.alert(error.message);
      })
  }

  // Sign up with email/password
  SignUp(email, password) {
    return firebase.auth().createUserWithEmailAndPassword(email, password)
      .then((result) => {
        /* Call the SendVerificationMail() function when new user sign
        up and returns promise */
        this.SendVerificationMail();
        this._httpClient.post("http://localhost:8080/users/create-user", result.user, httpOptions)
          .subscribe(response => {
            console.log("Response SignUp = ", response)
          })
      }).catch((error) => {
        window.alert(error.message)
      })
  }

  //Send email verification when new user sign up
  SendVerificationMail() {
    return firebase.auth().currentUser.sendEmailVerification()
      .then(() => {
        this.router.navigate(['verify-email-address']);
      })
  }

  // Reset Forgot password
  ForgotPassword(passwordResetEmail) {
    return firebase.auth().sendPasswordResetEmail(passwordResetEmail)
      .then(() => {
        window.alert('Password reset email sent, check your inbox.');
      }).catch((error) => {
        window.alert(error)
      })
  }

  // Returns true when user is logged in and email is verified
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
          this._httpClient.post("http://localhost:8080/users/check-user", result.user, httpOptions)
            .subscribe(response => {
              console.log("Response AuthLogin = ", response)
              this.router.navigate(['dashboard']);
            })
        })
      }).catch((error) => {
        window.alert(error)
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
