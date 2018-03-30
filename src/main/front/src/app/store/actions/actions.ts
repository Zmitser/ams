import {Action} from "@ngrx/store";
import {User} from "../../shared/models/user";
import {NavigationExtras} from "@angular/router";
import {ServerDataSource} from "ng2-smart-table";
import {Credentials, Weather} from "../../shared";

export const LOGIN_USER_ACTION = 'LOGIN_USER_ACTION';
export const LOGIN_USER_ACTION_SUCCESS = 'LOGIN_USER_ACTION_SUCCESS';
export const LOAD_USERS_ACTION = 'LOAD_USERS_ACTION';
export const LOAD_USERS_ACTION_SUCCESS = "LOAD_USERS_ACTION_SUCCESS";
export const CREATE_USERS_DATA_SOURCE = "CREATE_USERS_DATA_SOURCE";
export const CREATE_USERS_DATA_SOURCE_SUCCESS = "CREATE_USERS_DATA_SOURCE_SUCCESS";
export const DELETE_USER_ACTION = "DELETE_USER_ACTION";
export const DELETE_USER_ACTION_SUCCESS = "DELETE_USER_ACTION_SUCCESS";
export const CANCEL_SELECTED_USER = "CANCEL_SELECTED_USER";
export const CANCEL_SELECTED_USER_SUCCESS = "CANCEL_SELECTED_USER_SUCCESS";
export const GET_USER_ACTION = "GET_USER_ACTION";
export const GET_USER_ACTION_SUCCESS = "GET_USER_ACTION_SUCCESS";
export const UPDATE_USER_ACTION = "UPDATE_USER_ACTION";
export const UPDATE_USER_ACTION_SUCCESS = "UPDATE_USER_ACTION_SUCCESS";
export const GET_EMPTY_USER_ACTION = "GET_EMPTY_USER_ACTION";
export const GET_EMPTY_USER_ACTION_SUCCESS = "GET_EMPTY_USER_ACTION_SUCCESS";
export const LOAD_WEATHER_ACTION = "LOAD_WEATHER_ACTION";
export const LOAD_WEATHER_ACTION_SUCCESS = "LOAD_WEATHER_ACTION_SUCCESS";

export const GO = '[Router] Go';
export const BACK = '[Router] Back';

export interface ActionWithPayload<T> extends Action {
    payload: T;

}


export class LoadUsersAction implements Action {
    readonly type: string = LOAD_USERS_ACTION;

}

export class LoadUsersActionSuccess implements ActionWithPayload<User[]> {
    readonly type: string = LOAD_USERS_ACTION_SUCCESS;


    constructor(public payload: User[]) {
    }
}

export class DeleteUserActionAction implements ActionWithPayload<number> {
    readonly type: string = DELETE_USER_ACTION;

    constructor(public payload: number) {
    }
}

export class CancelSelectedUser implements ActionWithPayload<number> {
    readonly type: string = CANCEL_SELECTED_USER;

    constructor(public payload: number) {

    }
}

export class CancelSelectedUserSuccess implements ActionWithPayload<number> {
    readonly type: string = CANCEL_SELECTED_USER_SUCCESS;

    constructor(public payload: number) {

    }
}


export class DeleteUserActionSuccess implements ActionWithPayload<number> {
    readonly type: string = DELETE_USER_ACTION_SUCCESS;

    constructor(public payload: number) {

    }
}


export class GetUserAction implements ActionWithPayload<number> {
    readonly type: string = GET_USER_ACTION;

    constructor(public payload: number) {

    }
}

export class GetEmptyUserAction implements Action {
    readonly type: string = GET_EMPTY_USER_ACTION;

    constructor() {

    }
}

export class GetEmptyUserActionSuccess implements ActionWithPayload<User> {
    readonly type: string = GET_EMPTY_USER_ACTION_SUCCESS;

    constructor(public payload: User) {

    }
}

export class GetUserActionSuccess implements ActionWithPayload<User> {
    readonly type: string = GET_USER_ACTION_SUCCESS;

    constructor(public payload: User) {

    }
}

export class UpdateUserAction implements ActionWithPayload<User> {
    readonly type: string = UPDATE_USER_ACTION;

    constructor(public payload: User) {
    }
}

export class UpdateUserActionSuccess implements ActionWithPayload<User> {
    readonly type: string = UPDATE_USER_ACTION_SUCCESS;

    constructor(public payload: User) {
    }
}

export class CreateUsersDataSourceAction implements Action {
    readonly type: string = CREATE_USERS_DATA_SOURCE;

}

export class CreateUsersDataSourceActionSuccess implements ActionWithPayload<ServerDataSource> {
    readonly type: string = CREATE_USERS_DATA_SOURCE_SUCCESS;


    constructor(public payload: ServerDataSource) {
    }
}

export class LoadWeatherAction implements Action {
    readonly type: string = LOAD_WEATHER_ACTION;

    constructor() {
    }
}


export class LoadWeatherActionSuccess implements ActionWithPayload<Map<string, Weather[]>> {
    type: string = LOAD_WEATHER_ACTION_SUCCESS;

    constructor(public payload: Map<string, Weather[]>) {

    }
}


export class LoginUserAction implements ActionWithPayload<Credentials> {
    type: string = LOGIN_USER_ACTION;

    constructor(public payload: Credentials) {

    }
}

export class LoginUserActionSuccess implements ActionWithPayload<boolean> {
    type: string = LOGIN_USER_ACTION_SUCCESS;

    constructor(public payload: boolean) {

    }
}

export class Go implements Action {
    readonly type = GO;

    constructor(public payload: {
        path: any[];
        query?: object;
        extras?: NavigationExtras;
    }) {
    }
}

export class Back implements Action {
    readonly type = BACK;
}

