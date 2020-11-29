import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {Subscription} from "rxjs";
import {SpotifyService} from "../../../../shared/services/spotify.service";
import {UserService} from "../../../../shared/services/user.service";

@Component({
  selector: 'app-search-albums',
  templateUrl: './search-albums.component.html',
  styleUrls: ['./search-albums.component.css']
})
export class SearchAlbumsComponent implements OnInit, OnDestroy {

  search: string = "";
  data: any[] = [];

  @Input()
  widget;
  @Input()
  resizeEvent: EventEmitter<GridsterItem>;
  @Output()
  removeWidget = new EventEmitter<any>();

  resizeSub: Subscription;

  constructor(public spotifyService: SpotifyService, public userService: UserService) { }

  ngOnInit(): void {
    this.resizeSub = this.resizeEvent.subscribe((widget) => {
      if (widget === this.widget) {
        let user = JSON.parse(localStorage.getItem("user"));
        let data = {
          "title": "SearchAlbum",
          "name": "SpotifySearchAlbum",
          "description": "Search an artist with Spotify",
          "params": null,
          "position": widget,
        }
        this.userService.updateWidget(user.uid, data)
            .subscribe((response: any) => {
              localStorage.removeItem("user")
              localStorage.setItem("user", JSON.stringify(response));
            });
      }
    })
  }

  ngOnDestroy(): void {
    this.resizeSub.unsubscribe();
  }

  /**
   * Remove the widget
   * @param $event
   * @param item
   */

  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

  /**
   * Get the data from Spotify API (album)
   */

  getAlbum() {
    this.spotifyService.getAlbums(this.search)
        .subscribe(response => {
          this.data.length = 0;
          this.data = response;
          this.search = "";
        });
  }
}
