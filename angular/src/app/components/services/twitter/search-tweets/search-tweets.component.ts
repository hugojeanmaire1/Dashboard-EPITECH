import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {Subscription} from "rxjs";
import {TwitterService} from "../../../../shared/services/twitter.service";

@Component({
  selector: 'app-search-tweets',
  templateUrl: './search-tweets.component.html',
  styleUrls: ['./search-tweets.component.css']
})
export class SearchTweetsComponent implements OnInit, OnDestroy {
  search: string = "";
  data: any[] = [];

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

  searchTweet() {
    this.twitterService.searchTweet(this.search)
      .subscribe(response => {
        this.data.length = 0;
        this.data = response;
        console.log(this.data);
        this.search = "";
      });
  }
}
