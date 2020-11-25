import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Services} from "../../../shared/services/services";
import {NestedTreeControl} from "@angular/cdk/tree";
import {UserService} from "../../../shared/services/user.service";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  treeControl = new NestedTreeControl<Services>(node => node.widgets);
  dataSource: Services[] = [];

  @Output()
  addItem = new EventEmitter<any>();

   constructor(public userService: UserService) {
  }

  hasChild = (_: number, node: Services) => !!node.widgets && node.widgets.length > 0;

   ngOnInit(): any {
     this.userService.getServices().subscribe(response => {
       this.dataSource = response;
     })
  }

  addWidget(widget) {
     this.addItem.emit(widget);
  }

}
