import { Component, OnInit } from '@angular/core';
import {CompactType, GridsterConfig, GridsterItem, GridsterItemComponent, GridType} from 'angular-gridster2';
import {TwitterService} from "../../../shared/services/twitter.service";

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
  };

  removeItem($event: MouseEvent | TouchEvent, item): void {
    $event.preventDefault();
    $event.stopPropagation();
    this.dashboard.splice(this.dashboard.indexOf(item), 1);
  }

  addItem(): void {
    this.dashboard.push({cols: 2, rows: 1, y: 0, x: 0});
  }

  addTimelineWidget(): void {}

  // callTimeLine() {
  //   this.twitterService.getTimeline();
  // }

}
