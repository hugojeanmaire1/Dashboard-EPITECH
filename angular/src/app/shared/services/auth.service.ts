import { Injectable, NgZone } from '@angular/core';
import { User } from "../services/user";
import firebase from 'firebase/app';
import "firebase/auth";
import 'firebase/firestore';
import { AngularFirestore, AngularFirestoreDocument } from '@angular/fire/firestore';
import { Router } from "@angular/router";

@Injectable({
  providedIn: 'root'
})

export class AuthService {
  userData: any; // Save logged in user data

  constructor(
    public afs: AngularFirestore,     // Inject Firestore service
    public router: Router,
    public ngZone: NgZone,            // NgZone service to remove outside scope warning
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
            this.router.navigate(['test'])
          });
          console.log(result.user);
          this.SetUserData(result.user);
      }).catch((error) => {
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
    return (user !== null && user.emailVerified !== false);
  }

  // Sign in with Google
  GoogleAuth() {
    return this.AuthLogin(new firebase.auth.GoogleAuthProvider());
  }

  // Auth logic to run auth providers
  AuthLogin(provider) {
    return firebase.auth().signInWithPopup(provider)
      .then((result) => {
        this.ngZone.run(() => {
          this.router.navigate(['test']);
        })
        this.SetUserData(result.user);
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
      emailVerified: user.emailVerified
    }
    return userRef.set(userData, {
      merge: true
    })
  }

  // Sign out
  SignOut() {
    return firebase.auth().signOut().then(() => {
      localStorage.removeItem('user');
      this.router.navigate(['sign-in']);
    })
  }
}
