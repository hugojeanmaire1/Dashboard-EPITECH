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

/**
 * Authentification Service
 * List all the functions for Authentification of the app using Firebase
 */
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

  /**
   * @example
   * SignIn(prenom.nom@epitech.eu, ABCDE12345)
   *
   * @param email User email
   * @param password User password
   * @return the user infos and redirect to /dashboard
   */
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

  /**
   * @example
   * SignUp(penom.nom@epitech.eu, ABCDE12345)
   *
   * @param {string} email User email
   * @param {string} password User password
   * @return the user infos and send verification email then redirect to /dashboard
   */
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

  /**
   * Firebase send email to verify is profil
   * @return send Verification email and redirect to /verify-email-address
   */
  SendVerificationMail() {
    return firebase.auth().currentUser.sendEmailVerification()
      .then(() => {
        this.router.navigate(['verify-email-address']);
      })
  }

  /**
   * Send a email for reset password
   * @param passwordResetEmail
   * @return Email reset password
   */
  ForgotPassword(passwordResetEmail) {
    return firebase.auth().sendPasswordResetEmail(passwordResetEmail)
      .then(() => {
        window.alert('Password reset email sent, check your inbox.');
      }).catch((error) => {
        window.alert(error)
      })
  }

  /**
   * Verify if the user is connected
   */
  get isLoggedIn(): boolean {
    const user = JSON.parse(localStorage.getItem('user'));
    return (user !== null);
  }

  /**
   * Google authentification to firebase
   * @constructor
   */
  GoogleAuth() {
    return this.AuthLogin(new firebase.auth.GoogleAuthProvider());
  }

  /**
   * Github authentification to firebase
   * @constructor
   */
  GithubAuth() {
    return this.AuthLogin(new firebase.auth.GithubAuthProvider());
  }

  /**
   * Twitter authentification to firebase
   * @constructor
   */
  TwitterAuth() {
    return this.AuthLogin(new firebase.auth.TwitterAuthProvider());
  }

  /**
   * Send the user to the login platform of the provider
   * @param provider
   * @return User infos and redirect to /dashboard
   */
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

  /**
   * @return User data stock in localStorage
   */
  getUserData() {
    console.log(this.userData);
    return this.userData;
  }

  /**
   * User logout
   * Redirect to /sign-in
   */
  SignOut() {
    return firebase.auth().signOut().then(() => {
      localStorage.removeItem('user');
      this.router.navigate(['sign-in']);
    })
  }

}
