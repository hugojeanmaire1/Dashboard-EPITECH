import {
  Component,
  Input,
  OnInit,
  EventEmitter,
  OnDestroy,
  Output
} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {TwitterService} from "../../../../shared/services/twitter.service";
import {Subscription} from 'rxjs';
import {UserService} from "../../../../shared/services/user.service";

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})

export class TimelineComponent implements OnInit, OnDestroy {
  data: any[] = [];
  timelineUsername: string = "Trashtalk_fr";
  find: boolean = false;
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
          "title": "Timeline",
          "name": "TwitterTimeline",
          "description": "See timeline of a user",
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
      if (this.timelineUsername !== "" && this.find === true) {
        this.callTimeLine(this.timelineUsername);
        console.log("Refresh !");
      }
    }, 10000);
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
   * Function calling a Timeline from a specific @
   */
  callTimeLine(screenName: string) {
    this.twitterService.getTimeline(screenName)
      .subscribe(response => {
        this.data = response;
        this.find = true;
      }, error => {
        alert("You're not connected to Twitter")
      })
  }

  /**
   * Function who can change the @
   */
  changeSearch() {
    this.find = false;
    this.timelineUsername = "";
    this.data.length = 0;
  }

}
