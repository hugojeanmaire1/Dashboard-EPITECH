import { Action } from "@ngrx/store";
import { User } from "../../shared/services/user";

export enum UserActionTypes {
  LOGIN = '[User] LOGIN',
  LOGIN_COMPLETE = '[User] LOGIN_COMPLETE',
  LOGIN_ERROR = '[User] LOGIN_ERROR',
}

export class Login implements Action {
  readonly type = UserActionTypes.LOGIN;

  constructor(public payload: any) { }
}

export class LoginComplete implements Action {
  readonly type = UserActionTypes.LOGIN_COMPLETE;
  constructor(public payload: any) {}
}
export class LoginError implements Action {
  readonly type = UserActionTypes.LOGIN_ERROR;
  constructor(public payload: any) {}
}

export type UserActions
  = Login
  | LoginComplete
  | LoginError;
