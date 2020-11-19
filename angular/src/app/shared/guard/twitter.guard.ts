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

@Injectable({
  providedIn: 'root'
})
export class TwitterGuard implements CanActivate {
  constructor(
    private router: Router,
    private _httpClient: HttpClient,
    ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let data = route.queryParams
    let body = JSON.parse(localStorage.getItem('user'))
    this._httpClient.post("http://localhost:8080/services/twitter/login/callback" + "?oauth_verifier=" + data.oauth_verifier + "&oauth_token=" + data.oauth_token, body, httpOptions)
      .subscribe(response => {
        localStorage.setItem('user', JSON.stringify(response));
        this.router.navigate(['dashboard']);
      })

    return true;
  }

}
