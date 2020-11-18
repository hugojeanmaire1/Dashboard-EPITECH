import { Component, OnInit } from '@angular/core';
import {CompactType, GridsterConfig, GridsterItem, GridsterItemComponent, GridType} from 'angular-gridster2';
import {TwitterService} from "../../../shared/services/twitter.service";
import { Store } from '@ngrx/store';
import {Observable} from "rxjs";

@Component({
  selector: 'app-twitch',
  templateUrl: './twitch.component.html',
  styleUrls: ['./twitch.component.css']
})
export class TwitchComponent implements OnInit {
  dashboard: Array<GridsterItem>;

  constructor(
    private store: Store<any>,
    public twitterService: TwitterService,
  ) { }

  ngOnInit(): void {
    this.dashboard = [
      {cols: 2, rows: 1, y: 0, x: 4},
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
