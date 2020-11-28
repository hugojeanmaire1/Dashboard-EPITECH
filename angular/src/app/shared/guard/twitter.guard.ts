import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router
} from '@angular/router';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders} from "@angular/common/http";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': "application/json",
  })
};

/**
 * Guard for Twitter Login
 */
@Injectable({
  providedIn: 'root'
})
export class TwitterGuard implements CanActivate {
  constructor(
    private router: Router,
    private _httpClient: HttpClient,
    ) { }

  /**
   * Get all the Twitter data of the user in the database
   * @param route
   * @param state
   */
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let data = route.queryParams
    let body = JSON.parse(localStorage.getItem('user'))
    this._httpClient.post("http://localhost:8080/services/twitter/login/callback" + "?oauth_verifier=" + data.oauth_verifier + "&oauth_token=" + data.oauth_token, body, httpOptions)
      .subscribe(response => {
        localStorage.setItem('user', JSON.stringify(response));
        console.log("LocalStorage User = ", JSON.parse(localStorage.getItem("user")));
        this.router.navigate(['dashboard']);
      })

    return true;
  }

}
