import {ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation, EventEmitter} from '@angular/core';
import {CompactType, GridsterConfig, GridsterItem, GridType} from 'angular-gridster2';
import {AuthService} from "../../shared/services/auth.service";
import {TwitterService} from "../../shared/services/twitter.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  encapsulation: ViewEncapsulation.None
})

export class DashboardComponent implements OnInit {
  options: GridsterConfig;
  dashboard: Array<GridsterItem>;
  resizeEvent: EventEmitter<GridsterItem> = new EventEmitter<GridsterItem>();
  showFiller = false;

  constructor(public authService: AuthService, public twitterService: TwitterService) {}

  ngOnInit(): void {
    this.options = {
      gridType: GridType.Fit,
      compactType: CompactType.None,
      displayGrid: "none",
      //maxCols: 10,
      disableWarnings: false,
      ignoreMarginInRow: false,
      pushItems: true,
      draggable: {
        enabled: true
      },
      // resizable: {
      //   enabled: true
      // },
      itemResizeCallback: (item) => {
        // update DB with new size
        // send the update to widgets
        this.resizeEvent.emit(item);
      },
    };

    this.dashboard = [
      {cols: 2, rows: 4, y: 0, x: 0, type: 'TwitterTimeline'},
      {cols: 2, rows: 4, y: 0, x: 2, type: 'TwitterSearchTweet'},
      {cols: 2, rows: 2, y: 4, x: 0, type: 'TwitterPostTweet'},
      {cols: 2, rows: 2, y: 0, x: 4, type: 'SpotifySearch'},
      {cols: 2, rows: 2, y: 2, x: 4, type: 'TwitchTopGames'},
    ];
  }

  getUserId () {
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
  }

  addItem(): void {
    this.dashboard.push({cols: 2, rows: 4, y: 0, x: 2, type: 'TwitterTimeline'});
  }
}
