import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnDestroy,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {TwitterService} from "../../../../shared/services/twitter.service";
import {Subscription} from "rxjs";
import {UserService} from "../../../../shared/services/user.service";

@Component({
  selector: 'app-post-tweet',
  templateUrl: './post-tweet.component.html',
  styleUrls: ['./post-tweet.component.css']
})
export class PostTweetComponent implements OnInit, OnDestroy {
  input: string;

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
          "title": "Post a tweet",
          "name": "TwitterPostTweet",
          "description": "Post a tweet in the user timeline.",
          "params": null,
          "position": widget,
        }
        this.userService.addWidget(user.uid, "twitter", data);
      }
    })
  }

  ngOnDestroy(): void {
    this.resizeSub.unsubscribe();
  }

  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

  postTweet() {
    this.twitterService.postTweet(this.input);
    this.input = "";
  }
}
