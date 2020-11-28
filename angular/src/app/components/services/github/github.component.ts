import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {TwitterService} from "../../../shared/services/twitter.service";
import {GithubService} from "../../../shared/services/github.service";

@Component({
  selector: 'app-github',
  templateUrl: './github.component.html',
  styleUrls: ['./github.component.css']
})
export class GithubComponent implements OnInit {
  @Input()
  widget;
  @Input()
  resizeEvent;
  @Output()
  removeWidget = new EventEmitter();


  constructor(public githubService: GithubService) { }

  ngOnInit(): void {
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
