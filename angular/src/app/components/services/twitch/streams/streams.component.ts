import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {Subscription} from "rxjs";
import {TwitchService} from "../../../../shared/services/twitch.service";

@Component({
  selector: 'app-streams',
  templateUrl: './streams.component.html',
  styleUrls: ['./streams.component.css']
})

export class StreamsComponent implements OnInit, OnDestroy {
  data: any[] = [];
  interval: any;

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

    this.twitchService.getStreams()
      .subscribe(response => {
        this.data = response.streams;
      })

    this.interval = setInterval(() => {
      this.twitchService.getStreams()
        .subscribe(response => {
          this.data = response.streams;
        });
    }, 30000);
  }

  ngOnDestroy(): void {
    clearInterval(this.interval);
    this.resizeSub.unsubscribe();
  }

  /**
   * Remove the Widget
   * @param $event
   * @param item
   */

  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }
}
