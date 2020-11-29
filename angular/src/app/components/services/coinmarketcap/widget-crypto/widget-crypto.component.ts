import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {Subscription} from "rxjs";
import {UserService} from "../../../../shared/services/user.service";
import {CmcService} from "../../../../shared/services/cmc.service";

@Component({
  selector: 'app-widget-crypto',
  templateUrl: './widget-crypto.component.html',
  styleUrls: ['./widget-crypto.component.css']
})

/**
 * Widget to find a cryptocurrency
 */
export class WidgetCryptoComponent implements OnInit, OnDestroy {

  @Input()
  widget;
  @Input()
  resizeEvent: EventEmitter<GridsterItem>;
  @Output()
  removeWidget = new EventEmitter<any>();

  resizeSub: Subscription;
  subscriber: Subscription;
  data: any;
  input: string;
  find: boolean;
  find_data: boolean;

  /**
   * Cryptocurrency constructor
   * @param userService
   * userService to save data
   * @param cmc
   * cmc service to get data
   */
  constructor(private userService: UserService, private cmc: CmcService) {}

  /**
   * NgOnInit function implementation
   */
  ngOnInit(): void {
    this.resizeSub = this.resizeEvent.subscribe((widget) => {
      if (widget === this.widget) {
        let user = JSON.parse(localStorage.getItem("user"));
        let data = {
          "title": "Find Crypto information",
          "name": "FindCrypto",
          "description": "Find Crypto information",
          "params": null,
          "position": widget,
        }
        this.userService.updateWidget(user.uid, data)
            .subscribe(response => {
              localStorage.removeItem("user")
              localStorage.setItem("user", JSON.stringify(response));
            });
      }
    })
  }

  /**
   * NgOnDestroy function impl
   */
  ngOnDestroy(): void {
    if (this.resizeSub)
      this.resizeSub.unsubscribe();
    if (this.subscriber)
      this.subscriber.unsubscribe();
  }

  /**
   * Remove widget
   * @param $event
   * event of the gridster
   * @param item
   * item to remove
   */
  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

  /**
   * Get cryptocurrency data
   */
  getCrypto() {
    this.subscriber = this.cmc.getCrypto(this.input).subscribe(data => {
      console.log(data);
      if (data == null) {
        alert("Please set a good value for the cryptocurrency");
        return;
      }
      this.data = data;
    });
    this.input = "";
  }



}
