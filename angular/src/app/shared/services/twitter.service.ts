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
/**
 * Twitter functions to call the backend
 */
export class TwitterService {
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
  ) {}

  /**
   * Handle error
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
   * Get a list of the tweets of a user
   * @param screenName {string} name
   */
  getTimeline(screenName: string): Observable<any> {
    return this._httpClient.get<any[]>('http://localhost:8080/services/twitter/timeline?user=' + screenName);
  }

  /**
   * Post a tweet in the user timeline
   * @param input {string} tweet
   */
  postTweet(input) {
    return this._httpClient.get<any[]>('http://localhost:8080/services/twitter/tweet/post/' + input)
        .subscribe((data) => { console.log(data)});
  }

  /**
   * Get a list of tweets of a subject
   * @param search {string} name
   */
  searchTweet(search: string) {
    return this._httpClient.get<any[]>('http://localhost:8080/services/twitter/search/tweet?search=' + search)
      .pipe(map(data => data));
  }
}
