import { Component, OnInit } from '@angular/core';
import {Services} from "../../../shared/services/services";
import {NestedTreeControl} from "@angular/cdk/tree";
import {MatTreeNestedDataSource} from "@angular/material/tree";

const TREE_SERVICES: Services[] = [
  {
    name: "Twitter",
    widgets: [
      { name: "Follow" },
      { name: "Timeline" },
    ]
  },
  {
    name: "Twitch",
    widgets: [
      { name: "Live" },
      { name: "Favorite" },
    ]
  },
  {
    name: "Spotify",
    widgets: [
      { name: "Listen" },
      { name: "Favorite" },
    ]
  },
  {
    name: "Youtube",
    widgets: [
      { name: "Explorer" },
      { name: "Abonnements" },
    ]
  },
  {
    name: "Weather",
    widgets: [
      { name: "Town" },
    ]
  }
]

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  treeControl = new NestedTreeControl<Services>(node => node.widgets);
  dataSource = new MatTreeNestedDataSource<Services>();

  constructor() {
    this.dataSource.data = TREE_SERVICES;
  }

  hasChild = (_: number, node: Services) => !!node.widgets && node.widgets.length > 0;

  ngOnInit(): void {
  }

}
