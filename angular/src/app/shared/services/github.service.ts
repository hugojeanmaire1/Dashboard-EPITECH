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

  getProjectOrg(organization: string) {
    return this._httpClient.get<any[]>('http://localhost:8080/services/github/org?organization=' + organization).subscribe((data) => {
      console.log(data);
    });
  }

  getOrganisation()
  {
    let body = JSON.parse(localStorage.getItem('user'));
    return this._httpClient.post<any[]>("http://localhost:8080/services/github/organizations", body, httpOptions).subscribe((data) => {
      console.log(data);
    });
  }
}
