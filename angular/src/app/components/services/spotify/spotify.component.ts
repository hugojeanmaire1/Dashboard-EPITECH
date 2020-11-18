import { Component, OnInit } from '@angular/core';
import {CompactType, GridsterConfig, GridsterItem, GridType} from 'angular-gridster2';

import { Store } from '@ngrx/store';
import {Observable} from "rxjs";

@Component({
  selector: 'app-spotify',
  templateUrl: './spotify.component.html',
  styleUrls: ['./spotify.component.css']
})
export class SpotifyComponent implements OnInit {
  //options: GridsterConfig;
  dashboard: Array<GridsterItem>;

  constructor() { }

  ngOnInit(): void {
    this.dashboard = [
      {cols: 2, rows: 1, y: 0, x: 2},
      {cols: 2, rows: 1, y: 1, x: 2},
    ];
  }

  removeItem($event: MouseEvent | TouchEvent, item): void {
    $event.preventDefault();
    $event.stopPropagation();
    this.dashboard.splice(this.dashboard.indexOf(item), 1);
  }

  addItem(): void {
    this.dashboard.push({x: 0, y: 0, cols: 1, rows: 1});
  }

}
