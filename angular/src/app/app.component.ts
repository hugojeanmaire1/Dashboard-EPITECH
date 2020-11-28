import { Component } from '@angular/core';
import { AuthService } from "./shared/services/auth.service";

/**
 * App Component
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
/**
 * @class Main component
 */
export class AppComponent {
  /**
   * @constructor je sais pas
   * @param authService Je sais pas
   */
  constructor(public authService: AuthService) {}

  /**
   * Title of the page
   */
  title = 'dashboard';
}
