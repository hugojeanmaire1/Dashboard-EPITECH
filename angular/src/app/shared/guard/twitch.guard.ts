import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders} from "@angular/common/http";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': "application/json",
  })
};

/**
 * Guard for Twitch Login
 */
@Injectable({
  providedIn: 'root'
})
export class TwitchGuard implements CanActivate {

  /**
   * @constructor
   * @param router
   * @param _httpClient
   */
  constructor(private router: Router, private _httpClient: HttpClient) {}

  /**
   * Get all the Twitch data of the user in the database
   * @param route
   * @param state
   */
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let data = route.queryParams
    console.log("DATA = ", data);
    let body = JSON.parse(localStorage.getItem('user'));
    this._httpClient.post("http://localhost:8080/services/twitch/login/callback?code=" + data.code, body, httpOptions)
        .subscribe(response => {
          localStorage.setItem('user', JSON.stringify(response));
          console.log("LocalStorage User = ", JSON.parse(localStorage.getItem("user")));
          console.log("twitch RESPONSE = ", response);
          this.router.navigate(['dashboard']);
        })
    return true;
  }

}
