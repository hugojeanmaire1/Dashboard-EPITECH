import { createReducer, on } from "@ngrx/store";
import {UserActions, UserActionTypes} from "../actions/store.actions";
import { User } from "../../shared/services/user";

// export interface State {
//   // is a user authenticated?
//   isAuthenticated: boolean;
//   // if authenticated, there should be a user object
//   user: null;
//   // error message
//   errorMessage: string | null;
// };

export const initialState = {
  isAuthenticated: false,
  user: null,
  errorMessage: null
};

export function reducer(state = initialState, action: UserActions) {
  switch (action.type) {
    case UserActionTypes.LOGIN:
      return {
        ...state,
        errorMessage: null,
        isAuthenticated: false,
      };
    case UserActionTypes.LOGIN_COMPLETE:
      return {
        ...state,
        isAuthenticated: true,
        user: action.payload,
        errorMessage: null,
      };
    case UserActionTypes.LOGIN_ERROR:
      return {
        ...state,
        errorMessage: action.payload,
        isAuthenticated: false,
      };
    default:
      return state;
  }
}

export const selectUser = (state) => state.user;
export const errorMessage = (state) => state.errorMessage;
export const isAuthenticated = (state) => state.isAuthenticated;
