import {Injectable, NgZone} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient, HttpHeaders} from "@angular/common/http";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': "application/json",
  })
};

@Injectable({
  providedIn: 'root'
})
export class CmcService {

  constructor(public router: Router, public ngZone: NgZone, private _httpClient: HttpClient) { }

  getCrypto(inputData: string)
  {
    return this._httpClient.get("http://localhost:8080/services/coinmarketcap/coins?coin=" + inputData, httpOptions);
  }

}
