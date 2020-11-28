import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {Subscription} from "rxjs";
import {TwitchService} from "../../../../shared/services/twitch.service";
import {strict} from "assert";

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit, OnDestroy {
  data: any[] = [];
  input: string = "";

  @Input()
  widget;
  @Input()
  resizeEvent: EventEmitter<GridsterItem>;
  @Output()
  removeWidget = new EventEmitter<any>();

  resizeSub: Subscription;

  constructor(public twitchService: TwitchService) { }

  ngOnInit(): void {
    this.resizeSub = this.resizeEvent.subscribe((widget) => {
      if (widget === this.widget) {
        console.log(widget);
      }
    })
  }

  ngOnDestroy(): void {
    this.resizeSub.unsubscribe();
  }

  /**
   * Remove widget
   * @param $event
   * @param item
   */

  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

  /**
   * Get a date
   * @param value
   */

  getDate(value) {
    let date = new Date(value);
    return date.toDateString();
  }

  /**
   * Searching a specific user with Twitch API
   * @param input
   */

  searchUser(input: string) {
    this.twitchService.getUser(input)
      .subscribe(response => {
        console.log(response);
        this.data = response.users;
        input = "";
      })
  }

}
