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

  @Input()
  widget;
  @Input()
  resizeEvent: EventEmitter<GridsterItem>;
  @Output()
  removeWidget = new EventEmitter<any>();

  resizeSub: Subscription;

  constructor(public githubService: GithubService, public userService: UserService) { }

  ngOnInit(): void {
    this.resizeSub = this.resizeEvent.subscribe((widget) => {
      if (widget === this.widget) {
        let user = JSON.parse(localStorage.getItem("user"));
        let data = {
          "title": "See Organization's project",
          "name": "GithubOrgProject",
          "description": "See Organization's project",
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
    this.githubService.getOrganisation();
  }

  ngOnDestroy(): void {
    this.resizeSub.unsubscribe();
  }

  removeItem($event: MouseEvent | TouchEvent, item): void {
    this.removeWidget.emit({event: $event, item: item});
  }

  findOrgProject() {
    this.githubService.getProjectOrg(this.input);
    this.input = "";
  }

}
