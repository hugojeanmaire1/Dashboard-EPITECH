import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CompactType, GridsterConfig, GridsterItem, GridType} from 'angular-gridster2';
import {SpotifyService} from "../../../shared/services/spotify.service";

@Component({
  selector: 'app-spotify',
  templateUrl: './spotify.component.html',
  styleUrls: ['./spotify.component.css']
})
export class SpotifyComponent implements OnInit {
  @Input()
  widget;
  @Input()
  resizeEvent;
  @Output()
  removeWidget = new EventEmitter();

  constructor(public spotifyService: SpotifyService) { }

  ngOnInit(): void {

  }

  /**
   * Remove the widget
   * @param $event
   * @param item
   */

  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

}
