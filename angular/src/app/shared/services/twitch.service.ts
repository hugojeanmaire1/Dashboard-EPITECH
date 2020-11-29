import { Injectable, NgZone } from '@angular/core';
import {HttpClient, HttpHeaders, HttpErrorResponse } from "@angular/common/http";
import { Router } from "@angular/router";
import { Observable, throwError } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
    "Access-Control-Allow-Origin": "*",
  })
};


@Injectable({
  providedIn: 'root'
})
/**
 * Twitch functions to call the backend
 */
export class TwitchService {

  /**
   *
   * @param router
   * @param ngZone
   * @param _httpClient
   */
  constructor(
    public router: Router,
    public ngZone: NgZone, // NgZone service to remove outside scope warning
    private _httpClient: HttpClient,

  ) {

  }


  /**
   * Handle Error in call to back-end
   * @param error
   */
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

  /**
   * Get most seen games on twitch
   */
  getTrends(): Observable<any> {
    return this._httpClient.get<any>("http://localhost:8080/services/twitch/trends");
  }

  /**
   * Get most viewed stream in France
   */
  getStreams(): Observable<any> {
    return this._httpClient.get<any>("http://localhost:8080/services/twitch/active-streams");
  }

  /**
   * Get info from a twitch user
   * @param login
   */
  getUser(login: string): Observable<any> {
    return this._httpClient.get<any>("http://localhost:8080/services/twitch/get-user?login=" + login);
  }
}
