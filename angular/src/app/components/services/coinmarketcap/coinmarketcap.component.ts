import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GithubService} from "../../../shared/services/github.service";

@Component({
  selector: 'app-coinmarketcap',
  templateUrl: './coinmarketcap.component.html',
  styleUrls: ['./coinmarketcap.component.css']
})
/**
 * CoinMarketCap Component Widget
 */
export class CoinmarketcapComponent {
  @Input()
  widget;
  @Input()
  resizeEvent;
  @Output()
  removeWidget = new EventEmitter();

  /**
   * Remove widget
   * @param $event
   * @param item
   */
  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

}
