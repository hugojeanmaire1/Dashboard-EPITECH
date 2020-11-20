import {
  Component,
  Injectable,
  Input,
  OnInit,
  EventEmitter,
  OnChanges,
  SimpleChanges,
  OnDestroy,
  Output
} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {TwitterService} from "../../../../shared/services/twitter.service";
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})

export class TimelineComponent implements OnInit, OnDestroy {
  data: any[] = [];
  dashboard: any[] = [];

  @Input()
  widget;
  @Input()
  resizeEvent: EventEmitter<GridsterItem>;
  @Output()
  removeWidget = new EventEmitter<any>();

  resizeSub: Subscription;

  constructor(public twitterService: TwitterService) { }

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

  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

  callTimeLine() {
    this.twitterService.getTimeline()
      .subscribe(response => {
        this.data = response;
        console.log(this.data);
      })
  }

}
