import { Injectable, NgZone } from '@angular/core';
import {HttpClient, HttpHeaders, HttpClientJsonpModule, HttpErrorResponse } from "@angular/common/http";
import { Router } from "@angular/router";
import { Observable, throwError } from 'rxjs';
import {map} from "rxjs/operators";

const httpOptions = {
  headers: new HttpHeaders({
    "Access-Control-Allow-Origin": "*",
  })
};



@Injectable({
  providedIn: 'root'
})

export class TwitterService {
  constructor(
    public router: Router,
    public ngZone: NgZone, // NgZone service to remove outside scope warning
    private _httpClient: HttpClient,
  ) {

  }

  handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // Return an observable with a user-facing error message.
    return throwError(
      'Something bad happened; please try again later.');
  }

  LogIn() {
    // this._httpClient.jsonp('http://localhost:8080/services/twitter/login/', 'callback')
    //   .pipe(map(res => {
    //     console.log("Res = ", res);
    //     })
    //   )
    //   .subscribe(response => {
    //     console.log("Response = ", response);
    //   })
    // window.location.href='http://localhost:8080/services/twitter/login';
    // this._httpClient.get("http://localhost:8080/services/twitter/login")
    //   .subscribe(response => {
    //     console.log("Login Twitter = ", response)
    //   })

    // this._httpClient.get("http://localhost:8080/services/twitter/login", httpOptions)
    //   .subscribe(response => {
    //     console.log("Login Twitter = ", response);
    //   })
  }

  getTimeline() {
    return this._httpClient.get<any[]>('http://localhost:8080/services/twitter/timeline?user=' + "TrashTalk_fr")
      .pipe(map(data => data));
  }
}
