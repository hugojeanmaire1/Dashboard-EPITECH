import { Component, OnInit } from '@angular/core';
import {CompactType, GridsterConfig, GridsterItem, GridsterItemComponent, GridType} from 'angular-gridster2';
import {TwitterService} from "../../../shared/services/twitter.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-twitter',
  templateUrl: './twitter.component.html',
  styleUrls: ['./twitter.component.css']
})

export class TwitterComponent implements OnInit {
  dashboard: Array<GridsterItem>;

  constructor(public twitterService: TwitterService) { }

  ngOnInit(): void {

    this.dashboard = [
      {cols: 2, rows: 1, y: 0, x: 0},
    ];

    this.twitterService.LogIn();

  };

  removeItem($event: MouseEvent | TouchEvent, item): void {
    $event.preventDefault();
    $event.stopPropagation();
    this.dashboard.splice(this.dashboard.indexOf(item), 1);
  }

  addItem(): void {
    this.dashboard.push({x: 0, y: 0, cols: 1, rows: 1});
  }

}
