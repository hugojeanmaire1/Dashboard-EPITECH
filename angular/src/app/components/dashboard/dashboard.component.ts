import {ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation, EventEmitter} from '@angular/core';
import {CompactType, GridsterConfig, GridsterItem, GridType} from 'angular-gridster2';
import {AuthService} from "../../shared/services/auth.service";
import {TwitterService} from "../../shared/services/twitter.service";
import { UUID } from "angular2-uuid";
import {UserService} from "../../shared/services/user.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.None
})

export class DashboardComponent implements OnInit {
  options: GridsterConfig;
  dashboard: Array<GridsterItem> = [];
  resizeEvent: EventEmitter<GridsterItem> = new EventEmitter<GridsterItem>();
  showFiller = false;

  constructor(public authService: AuthService, public userService: UserService) {}

  ngOnInit(): void {
    this.options = {
      gridType: GridType.ScrollHorizontal,
      compactType: CompactType.None,
      displayGrid: "none",
      //maxCols: 10,
      //disableWarnings: false,
      //ignoreMarginInRow: false,
      pushItems: true,
      draggable: {
        enabled: true
      },
      resizable: {
        enabled: true
      },
      itemChangeCallback: (item) => {
        // update DB with new size
        // send the update to widgets
        this.resizeEvent.emit(item);
      },
    };

    // this.dashboard = [
    //   {cols: 2, rows: 4, y: 0, x: 0, type: 'TwitterTimeline', id: UUID.UUID()},
    //   {cols: 2, rows: 4, y: 0, x: 2, type: 'TwitterSearchTweet', id: UUID.UUID()},
    //   {cols: 2, rows: 2, y: 4, x: 0, type: 'TwitterPostTweet', id: UUID.UUID()},
    //   {cols: 2, rows: 2, y: 0, x: 4, type: 'SpotifySearch', id: UUID.UUID()},
    //   {cols: 2, rows: 2, y: 2, x: 4, type: 'TwitchTopGames', id: UUID.UUID()},
    // ];
  }

  getUserId() {
    let data = JSON.parse(localStorage.getItem('user'));
    return data.uid;
  }

  changedOptions() {
    if (this.options.api && this.options.api.optionsChanged) {
      this.options.api.optionsChanged();
    }
  };

  getColorServices(item) {
    if (item.type === 'TwitterTimeline' || item.type === 'TwitterPostTweet' || item.type === 'TwitterSearchTweet' ) {
      return '#00acee';
    }
    if (item.type === 'TwitchTopGames') {
      return '#6441a5';
    }
    if (item.type === 'SpotifySearch') {
      return '#1DB954';
    }
  }

  removeItem($event: MouseEvent | TouchEvent, item): void {
    $event.preventDefault();
    $event.stopPropagation();
    this.dashboard.splice(this.dashboard.indexOf(item), 1)
    this.userService.removeWidget(this.getUserId(), item);
  }

  getWidgetService(widgetTitle) {
    if (widgetTitle === "TwitterTimeline" || widgetTitle === 'TwitterPostTweet' || widgetTitle === "TwitterSearchTweet") {
      return "twitter";
    }
    if (widgetTitle === "TwitchTopGames") {
      return "twitch";
    }
    if (widgetTitle === "Spotify") {
      return "spotify";
    }
  }

  addItem(item): void {
    item.position.id = UUID.UUID()
    this.dashboard.push({
        cols: item.position.cols,
        rows: item.position.rows,
        y: item.position.y,
        x: item.position.x,
        type: item.name,
        id: item.position.id,
      });
    this.userService.addWidget(this.getUserId(), this.getWidgetService(item.position.title), item)
      .subscribe(response => {
        localStorage.removeItem("user")
        localStorage.setItem("user", JSON.stringify(response));
      });
  }
}
