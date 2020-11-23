import { Component, OnInit } from '@angular/core';
import {Services} from "../../../shared/services/services";
import {NestedTreeControl} from "@angular/cdk/tree";
import {MatTreeNestedDataSource} from "@angular/material/tree";

const TREE_SERVICES: Services[] = [
  {
    name: "Twitter",
    widgets: [
      { title: "Post a tweet", name: "TwitterPostTweet", description: "Post a tweet in the user timeline", params: null, positions: null },
      { title: "Search timeline", name: "TwitterTimeline", description: "Post a tweet in the user timeline", params: null, positions: null },
      { title: "Search", name: "TwitterSearchTweet", description: "Post a tweet in the user timeline", params: null, positions: null },
    ]
  },
  {
    name: "Twitch",
    widgets: [
      { title: "Live", name: "TwitchLive", description: "Post a tweet in the user timeline", params: null, positions: null },
      { title: "Favorite", name: "TwitchFavorite", description: "Post a tweet in the user timeline", params: null, positions: null },
    ]
  },
  {
    name: "Spotify",
    widgets: [
      { title: "Listen", name: "SpotifyListen", description: "Post a tweet in the user timeline", params: null, positions: null },
      { title: "Favorite", name: "SpotifyFavorite", description: "Post a tweet in the user timeline", params: null, positions: null },
    ]
  },
  {
    name: "Youtube",
    widgets: [
      { title: "Explorer", name: "YoutubeExpolorer", description: "Post a tweet in the user timeline", params: null, positions: null },
      { title: "Abonnements", name: "YoutubeAbonnements", description: "Post a tweet in the user timeline", params: null, positions: null },
    ]
  },
  {
    name: "Weather",
    widgets: [
      { title: "Town", name: "WeatherTown", description: "Post a tweet in the user timeline", params: null, positions: null },
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
