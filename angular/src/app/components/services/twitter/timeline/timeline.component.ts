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
  timelineUsername: string = "Trashtalk_fr";
  find: boolean = false;

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

  callTimeLine(screenName: string) {
    this.twitterService.getTimeline(screenName)
      .subscribe(response => {
        this.data = response;
        this.find = true;

        console.log(this.data);
      })
  }

  changeSearch() {
    this.find = false;
    this.timelineUsername = "";
    this.data.length = 0;
  }

}
