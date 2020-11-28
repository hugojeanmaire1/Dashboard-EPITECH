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
export class SpotifyService {

    constructor(
        public router: Router,
        public ngZone: NgZone, // NgZone service to remove outside scope warning
        private _httpClient: HttpClient,

    ) {

    }

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

    getArtist(artist: String) {
        return this._httpClient.get<any[]>('http://localhost:8080/services/spotify/search/artist?artist=' + artist)
            .pipe(map(data => data));
    }

    getAlbums(albums: String) {
        return this._httpClient.get<any[]>('http://localhost:8080/services/spotify/search/albums?albums=' + albums)
            .pipe(map(data => data));
    }

    getPlaylist(playlist: String) {
        return this._httpClient.get<any[]>('http://localhost:8080/services/spotify/search/playlist?playlist=' + playlist)
            .pipe(map(data => data));
    }
}
