import {Component, Injectable, OnInit, OnChanges, SimpleChanges} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {TwitterService} from "../../../../shared/services/twitter.service";

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})

export class TimelineComponent implements OnInit {
  dashboard: Array<GridsterItem>;
  data: any[] = [];

  constructor(public twitterService: TwitterService) { }

  ngOnInit(): void {
    this.dashboard = [
      {cols: 2, rows: 4, y: 0, x: 0}
    ];
  }

  removeItem($event: MouseEvent | TouchEvent, item): void {
    $event.preventDefault();
    $event.stopPropagation();
    this.dashboard.splice(this.dashboard.indexOf(item), 1);
  }

  addItem(): void {
    this.dashboard.push({cols: 2, rows: 4, y: 0, x: 0});
  }

  callTimeLine() {
    this.twitterService.getTimeline()
      .subscribe(response => {
        this.data = response;
        console.log(this.data);
      })
  }

}
