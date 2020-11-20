import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {CompactType, GridsterConfig, GridsterItem, GridsterItemComponent, GridType} from 'angular-gridster2';
import {TwitterService} from "../../../shared/services/twitter.service";

@Component({
  selector: 'app-twitter',
  templateUrl: './twitter.component.html',
  styleUrls: ['./twitter.component.css']
})

export class TwitterComponent implements OnInit {
  @Input()
  widget;
  @Input()
  resizeEvent;
  @Output()
  removeWidget = new EventEmitter();

  constructor(public twitterService: TwitterService) { }

  ngOnInit(): void {

  };

  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

}
