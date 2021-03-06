import {Injectable} from '@angular/core';
import {Actions, Effect} from '@ngrx/effects';
import {UserService} from '../../layout/users/user.service';
import "rxjs/add/operator/switchMap";
import "rxjs/add/operator/map";
import {
    CREATE_USERS_DATA_SOURCE,
    CreateUsersDataSourceActionSuccess,
    DELETE_USER_ACTION,
    DeleteUserActionAction,
    DeleteUserActionSuccess,
    GET_EMPTY_USER_ACTION,
    GET_USER_ACTION,
    GetEmptyUserAction,
    GetEmptyUserActionSuccess,
    GetUserAction,
    GetUserActionSuccess,
    LOAD_USERS_ACTION,
    LoadUsersAction,
    LoadUsersActionSuccess,
    UPDATE_USER_ACTION,
    UpdateUserAction,
    UpdateUserActionSuccess
} from "../actions/actions";
import {User} from "../../shared/models/user";

@Injectable()
export class UserServiceEffects {


    @Effect() findUsers$ = this._action$
        .ofType(LOAD_USERS_ACTION)
        .switchMap((action: LoadUsersAction) => this._userService.findAll())
        .map((data: User[]) => new LoadUsersActionSuccess(data));


    @Effect() createUsersDataSource = this._action$
        .ofType(CREATE_USERS_DATA_SOURCE)
        .switchMap(action => this._userService.createServerDataSource())
        .map(data => new CreateUsersDataSourceActionSuccess(data));


    @Effect() deleteUser$ = this._action$
        .ofType(DELETE_USER_ACTION)
        .map((action: DeleteUserActionAction) => action.payload)
        .switchMap((payload: number) => this._userService.delete(payload))
        .map((data: number) => new DeleteUserActionSuccess(data));

    @Effect() findUser$ = this._action$
        .ofType(GET_USER_ACTION)
        .map((action: GetUserAction) => action.payload)
        .switchMap((payload: number) => this._userService.findOne(payload))
        .map((data: User) => new GetUserActionSuccess(data));

    @Effect() emptyUser$ = this._action$
        .ofType(GET_EMPTY_USER_ACTION)
        .switchMap((payload: GetEmptyUserAction) => this._userService.createEmptyUser())
        .map((data: User) => new GetEmptyUserActionSuccess(data));

    @Effect() saveUser$ = this._action$
        .ofType(UPDATE_USER_ACTION)
        .map((action: UpdateUserAction) => action.payload)
        .switchMap((user: User) => this._userService.save(user))
        .map((user: User) => new UpdateUserActionSuccess(user));


    constructor(private _action$: Actions, private _userService: UserService) {
    }
}