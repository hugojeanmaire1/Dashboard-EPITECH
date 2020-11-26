import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GridsterItem} from "angular-gridster2";
import {UserService} from "../../../../shared/services/user.service";
import {GithubService} from "../../../../shared/services/github.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-organisation-project',
  templateUrl: './organisation-project.component.html',
  styleUrls: ['./organisation-project.component.css']
})
export class OrganisationProjectComponent implements OnInit {

  input: string;
  find: boolean = false;
  data;

  @Input()
  widget;
  @Input()
  resizeEvent: EventEmitter<GridsterItem>;
  @Output()
  removeWidget = new EventEmitter<any>();

  resizeSub: Subscription;
  subscriber: Subscription;

  constructor(public githubService: GithubService, public userService: UserService) { }

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
  }

  ngOnDestroy(): void {
    if (this.resizeSub)
      this.resizeSub.unsubscribe();
    if (this.subscriber)
      this.subscriber.unsubscribe();
  }

  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

  findOrgProject() {
    console.log(this.input);
    this.subscriber = this.githubService.getOrganisation(this.input).subscribe(data => {
      this.data = data;
    });
    this.input = "";
  }

}
