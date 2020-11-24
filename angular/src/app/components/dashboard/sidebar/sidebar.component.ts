import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Services} from "../../../shared/services/services";
import {NestedTreeControl} from "@angular/cdk/tree";
import {AuthService} from "../../../shared/services/auth.service";

// const TREE_SERVICES: Services[] = [
//   {
//     name: "Twitter",
//     widgets: [
//       { title: "Post a tweet", name: "TwitterPostTweet", description: "Post a tweet in the user timeline", positions: {cols: 2, rows: 2, y: 0, x: 0} },
//       { title: "Timeline", name: "TwitterTimeline", description: "Post a tweet in the user timeline", positions: {cols: 2, rows: 4, y: 0, x: 0} },
//       { title: "Search", name: "TwitterSearchTweet", description: "Post a tweet in the user timeline", positions: {cols: 2, rows: 4, y: 0, x: 0} },
//     ]
//   },
//   {
//     name: "Twitch",
//     widgets: [
//       { title: "Live", name: "TwitchTopGames", description: "Post a tweet in the user timeline", positions: {cols: 2, rows: 2, y: 0, x: 0} },
//       { title: "Favorite", name: "TwitchTopGames", description: "Post a tweet in the user timeline", positions: {cols: 2, rows: 2, y: 0, x: 0} },
//     ]
//   },
//   {
//     name: "Spotify",
//     widgets: [
//       { title: "Listen", name: "SpotifySearch", description: "Post a tweet in the user timeline", positions: {cols: 2, rows: 2, y: 0, x: 0} },
//       { title: "Favorite", name: "SpotifySearch", description: "Post a tweet in the user timeline", positions: {cols: 2, rows: 2, y: 0, x: 0} },
//     ]
//   },
//   {
//     name: "Linkedin",
//     widgets: [
//       { title: "Explorer", name: "LinkedinExplorer", description: "Post a tweet in the user timeline", positions: {cols: 2, rows: 2, y: 0, x: 0} },
//       { title: "Abonnements", name: "LinkedinAbonnement", description: "Post a tweet in the user timeline", positions: {cols: 2, rows: 2, y: 0, x: 0} },
//     ]
//   },
//   {
//     name: "Weather",
//     widgets: [
//       { title: "Town", name: "WeatherTown", description: "Post a tweet in the user timeline", positions: {cols: 2, rows: 2, y: 0, x: 0} },
//     ]
//   }
// ]

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

   constructor(public authService: AuthService) {
  }

  hasChild = (_: number, node: Services) => !!node.widgets && node.widgets.length > 0;

   ngOnInit(): any {
     this.authService.getServices().subscribe(response => {
       this.dataSource = response;
       console.log(this.dataSource);
     })
  }

  addWidget(widgetType, widgetPosition) {
    console.log("Emit = ", widgetPosition, widgetType)
     this.addItem.emit( {type: widgetType, position: widgetPosition});
  }

}
