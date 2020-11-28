import { Injectable, NgZone } from '@angular/core';
import {HttpClient, HttpHeaders, HttpErrorResponse } from "@angular/common/http";
import { Router } from "@angular/router";
import { Observable, throwError } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({
    "Access-Control-Allow-Origin": "*",
  })
};

/**
 * Service return all the user infos needed
 */
@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(
    public router: Router,
    public ngZone: NgZone, // NgZone service to remove outside scope warning
    private _httpClient: HttpClient,
  ) {}

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
   * Get all the services and widgets of each service
   */
  getServices(): Observable<any> {
    return this._httpClient.get<any>("http://localhost:8080/services/get", httpOptions);
  }

  /**
   * Get all widgets use by the user
   * @param uid
   */
  getUserWidgets(uid): Observable<any> {
    return this._httpClient.get("http://localhost:8080/users/get-widgets?uid=" + uid, httpOptions);
  }

  /**
   * Remove the widget from the user widgets list
   * @param uid
   * @param widget
   */
  removeWidget(uid, widget): Observable<any> {
    return this._httpClient.post<any>("http://localhost:8080/users/remove-widget?uid=" + uid, widget, httpOptions);
  }

  /**
   * Add the widget from the user widget list
   * @param uid
   * @param serviceName
   * @param widget
   */
  addWidget(uid, serviceName, widget): Observable<any> {
    return this._httpClient.post<any>("http://localhost:8080/users/add-widget?uid=" + uid + "&serviceName=" + serviceName, widget);
  }

  /**
   * Update the widget position from the user widget list
   * @param uid
   * @param widget
   */
  updateWidget(uid, widget): Observable<any> {
    return this._httpClient.post<any>("http://localhost:8080/users/update-widget?uid=" + uid, widget);
  }
}
