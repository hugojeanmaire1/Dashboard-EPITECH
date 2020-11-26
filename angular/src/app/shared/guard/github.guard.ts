import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
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
export class GithubGuard implements CanActivate {

  constructor(
      private router: Router,
      private _httpClient: HttpClient,
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let data = route.queryParams
    console.log("DATA = ", data);
    let body = JSON.parse(localStorage.getItem('user'));
    this._httpClient.post("http://localhost:8080/services/github/login/callback?code=" + data.code + "&state=" + data.state, body, httpOptions)
        .subscribe(response => {
          localStorage.setItem('user', JSON.stringify(response));
          console.log("LocalStorage User = ", JSON.parse(localStorage.getItem("user")));
          this.router.navigate(['dashboard']);
        })
    return true;
  }

}
