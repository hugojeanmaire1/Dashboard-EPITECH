import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CompactType, GridsterConfig, GridsterItem, GridsterItemComponent, GridType} from 'angular-gridster2';
import {TwitterService} from "../../../shared/services/twitter.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-twitch',
  templateUrl: './twitch.component.html',
  styleUrls: ['./twitch.component.css']
})
export class TwitchComponent implements OnInit {
  @Input()
  widget;
  @Input()
  resizeEvent;
  @Output()
  removeWidget = new EventEmitter();

  constructor(public twitterService: TwitterService) { }

  ngOnInit(): void {

  }

  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

}
