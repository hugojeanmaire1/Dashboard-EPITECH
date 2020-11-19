import {ChangeDetectionStrategy, Component, OnInit, ViewEncapsulation} from '@angular/core';
import {CompactType, GridsterConfig, GridsterItem, GridType} from 'angular-gridster2';
import {AuthService} from "../../shared/services/auth.service";
import {Observable} from "rxjs";
import {TwitterComponent} from "../services/twitter/twitter.component";
import {SpotifyComponent} from "../services/spotify/spotify.component";

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
  showFiller = false;
  data: Observable<any>;


  constructor(public authService: AuthService) {}

  ngOnInit(): void {

    this.options = {
      gridType: GridType.Fit,
      compactType: CompactType.None,
      maxCols: 10,
      pushItems: true,
      draggable: {
        enabled: true
      },
      resizable: {
        enabled: true
      }
    };

    // this.dashboard = [
    //   {cols: 3, rows: 1, y: 3, x: 0},
    //   {cols: 2, rows: 2, y: 0, x: 2},
    //   {cols: 1, rows: 1, y: 0, x: 4},
    //   {cols: 3, rows: 2, y: 1, x: 4},
    //   {cols: 1, rows: 1, y: 4, x: 5},
    //   {cols: 1, rows: 1, y: 2, x: 1},
    //   {cols: 2, rows: 2, y: 5, x: 5},
    //   {cols: 2, rows: 2, y: 3, x: 2},
    //   {cols: 2, rows: 1, y: 2, x: 2},
    //   {cols: 1, rows: 1, y: 3, x: 4},
    //   {cols: 1, rows: 1, y: 0, x: 6}
    // ];
  }

  getId () {
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
    this.dashboard.splice(this.dashboard.indexOf(item), 1);
  }

  addItem(): void {
    this.dashboard.push({x: 0, y: 0, cols: 1, rows: 1});
  }
}
