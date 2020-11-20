import {ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation, EventEmitter} from '@angular/core';
import {CompactType, GridsterConfig, GridsterItem, GridType} from 'angular-gridster2';
import {AuthService} from "../../shared/services/auth.service";

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

  constructor(public authService: AuthService) {}

  ngOnInit(): void {
    this.options = {
      gridType: GridType.Fit,
      compactType: CompactType.None,
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
      {cols: 2, rows: 1, y: 0, x: 2, type: 'TwitterPostTweet'},
      {cols: 2, rows: 3, y: 1, x: 2, type: 'SpotifySearch'},
      {cols: 2, rows: 2, y: 0, x: 4, type: 'TwitchTopGames'},
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

  removeItem($event: MouseEvent | TouchEvent, item): void {
    $event.preventDefault();
    $event.stopPropagation();
    this.dashboard.splice(this.dashboard.indexOf(item), 1)
  }

  addItem(): void {
    this.dashboard.push({cols: 2, rows: 4, y: 0, x: 2, type: 'TwitterTimeline'});
  }
}
