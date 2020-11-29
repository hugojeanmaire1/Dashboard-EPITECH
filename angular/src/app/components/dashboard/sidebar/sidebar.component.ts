import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Services} from "../../../shared/services/services";
import {NestedTreeControl} from "@angular/cdk/tree";
import {UserService} from "../../../shared/services/user.service";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
/**
 * Sidebar component
 */
export class SidebarComponent implements OnInit {
  /**
   * treeControl: je sais pas
   * dataSource: array of data send by the backend
   */
  treeControl = new NestedTreeControl<Services>(node => node.widgets);
  dataSource: Services[] = [];

  @Output()
  addItem = new EventEmitter<any>();

  /**
   * Constructor
   * @param userService service for get infos in the backend
   */
   constructor(public userService: UserService) {
  }

  /**
   * Ne sert plus à rien
   * @param _
   * @param node
   */
  hasChild = (_: number, node: Services) => !!node.widgets && node.widgets.length > 0;

  /**
   * do function on component init
   */
   ngOnInit(): any {
     this.userService.getServices().subscribe(response => {
       this.dataSource = response;
     })
  }

  /**
   * callback func
   * @param widget: widget infos
   */
  addWidget(widget) {
     this.addItem.emit(widget);
  }

}
