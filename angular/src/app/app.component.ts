import { Component } from '@angular/core';
import { AuthService } from "./shared/services/auth.service";

/**
 * Main Component
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(
    public authService: AuthService
  ) {
  }

  title = 'dashboard';
}
