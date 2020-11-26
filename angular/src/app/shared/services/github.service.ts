import {Injectable, NgZone} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': "application/json",
  })
};

@Injectable({
  providedIn: 'root'
})
export class GithubService {

  constructor(public router: Router, public ngZone: NgZone, private _httpClient: HttpClient) { }

  getOrganisation(inputData: string): Observable<Object>
  {
    let body = JSON.parse(localStorage.getItem('user'));
    return this._httpClient.post("http://localhost:8080/services/github/repositories?repo=" + inputData, body, httpOptions);
  }
}
