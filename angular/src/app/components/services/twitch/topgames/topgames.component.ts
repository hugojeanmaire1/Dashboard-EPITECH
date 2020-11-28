import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {Subscription} from "rxjs";
import {TwitchService} from "../../../../shared/services/twitch.service";

@Component({
  selector: 'app-topgames',
  templateUrl: './topgames.component.html',
  styleUrls: ['./topgames.component.css']
})
export class TopgamesComponent implements OnInit, OnDestroy {
  data: any[] = [];

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

    this.twitchService.getTrends()
      .subscribe(response => {
        this.data = response.top
      })
  }

  ngOnDestroy(): void {
    this.resizeSub.unsubscribe();
  }

  /**
   * Remove Widget
   * @param $event
   * @param item
   */

  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

}
