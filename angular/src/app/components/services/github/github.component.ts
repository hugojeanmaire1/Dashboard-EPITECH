import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GithubService} from "../../../shared/services/github.service";

@Component({
  selector: 'app-github',
  templateUrl: './github.component.html',
  styleUrls: ['./github.component.css']
})
/**
 * Github Component class to load github Widgets
 */
export class GithubComponent {
  @Input()
  widget;
  @Input()
  resizeEvent;
  @Output()
  removeWidget = new EventEmitter();

  /**
   * Remove Widget
   * @param $event
   * @param item
   */
  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

}
