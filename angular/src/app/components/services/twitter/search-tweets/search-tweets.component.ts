import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {Subscription} from "rxjs";
import {TwitterService} from "../../../../shared/services/twitter.service";
import {UserService} from "../../../../shared/services/user.service";

@Component({
  selector: 'app-search-tweets',
  templateUrl: './search-tweets.component.html',
  styleUrls: ['./search-tweets.component.css']
})
export class SearchTweetsComponent implements OnInit, OnDestroy {
  search: string = "";
  data: any[] = [];
  interval: any;

  @Input()
  widget;
  @Input()
  resizeEvent: EventEmitter<GridsterItem>;
  @Output()
  removeWidget = new EventEmitter<any>();

  resizeSub: Subscription;

  constructor(public twitterService: TwitterService, public userService: UserService) { }

  ngOnInit(): void {
    this.resizeSub = this.resizeEvent.subscribe((widget) => {
      if (widget === this.widget) {
        let user = JSON.parse(localStorage.getItem("user"));
        let data = {
          "title": "Search",
          "name": "TwitterSearchTweet",
          "description": "Get a tweet of a subject",
          "params": null,
          "position": widget,
        }
        this.userService.updateWidget(user.uid, data)
          .subscribe(response => {
            localStorage.removeItem("user")
            localStorage.setItem("user", JSON.stringify(response));
          });
      }
    })

    this.interval = setInterval(() => {
      if (this.search !== "") {
        this.searchTweet();
      }
    }, 10000)
  }

  ngOnDestroy(): void {
    this.resizeSub.unsubscribe();
    clearInterval(this.interval);
  }

  /**
   * Remove the widget
   * @param $event
   * @param item
   */

  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

  /**
   * Function to search tweet with a key word with tweeter API
   */

  searchTweet() {
    this.twitterService.searchTweet(this.search)
      .subscribe(response => {
        this.data.length = 0;
        this.data = response;
        this.search = "";
      }, error => {
        alert("You're not connected to Twitter")
      });
  }
}
