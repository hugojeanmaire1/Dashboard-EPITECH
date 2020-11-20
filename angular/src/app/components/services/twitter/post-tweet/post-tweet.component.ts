import {AfterViewInit, Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {TwitterService} from "../../../../shared/services/twitter.service";

@Component({
  selector: 'app-post-tweet',
  templateUrl: './post-tweet.component.html',
  styleUrls: ['./post-tweet.component.css']
})
export class PostTweetComponent implements OnInit {

  dashboard: Array<GridsterItem>;
  input: string;

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

  addItems(): void {
    this.dashboard.push({cols: 2, rows: 4, y: 0, x: 0});
  }

  postTweet() {
    this.twitterService.postTweet(this.input);
  }
}
