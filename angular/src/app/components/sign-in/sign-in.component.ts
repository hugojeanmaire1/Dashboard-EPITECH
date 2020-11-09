import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../shared/services/auth.service";

import {faCoffee} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {
  faCoffee = faCoffee;
  constructor(
    public authService: AuthService
  ) { }

  ngOnInit(): void {
  }

}
