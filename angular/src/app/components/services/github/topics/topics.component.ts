import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {Subscription} from "rxjs";
import {GithubService} from "../../../../shared/services/github.service";
import {UserService} from "../../../../shared/services/user.service";

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.css']
})
/**
 * Topic Class Widgets to load topic form github
 */
export class TopicsComponent implements OnInit {

  input: string;
  find: boolean = false;
  data: any;
  interval: any;

  @Input()
  widget;
  @Input()
  resizeEvent: EventEmitter<GridsterItem>;
  @Output()
  removeWidget = new EventEmitter<any>();

  resizeSub: Subscription;
  subscriber: Subscription;

  /**
   * Constructor of the class
   * @param githubService
   * github service to load data from github
   * @param userService
   * user service to save data and get data
   */
  constructor(public githubService: GithubService, public userService: UserService) { }

  /**
   * NgOnInit function to load component with data
   */
  ngOnInit(): void {
    this.resizeSub = this.resizeEvent.subscribe((widget) => {
      if (widget === this.widget) {
        let user = JSON.parse(localStorage.getItem("user"));
        let data = {
          "title": "Find all topic with name",
          "name": "GithubOrgProject",
          "description": "Find all topic with name",
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

    this.interval = setInterval(() => {
      this.findTopics()
    }, 10000);
  }

  /**
   * ngOnDestroy function to destroy data
   */
  ngOnDestroy(): void {
    if (this.resizeSub)
      this.resizeSub.unsubscribe();
    if (this.subscriber)
      this.subscriber.unsubscribe();
  }

  /**
   * Remote widget from the gridster
   * @param $event
   * event emit by the grid
   * @param item
   * item to remove
   */
  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

  /**
   * Find topic from github service
   */
  findTopics() {
    this.subscriber = this.githubService.getTopics(this.input).subscribe(data => {
      console.log(data);
      if (data == null)
        alert("You're not connected to Github");
      this.data = data;
    });
    this.input = "";
  }


}
