import {Injectable} from "@angular/core";
import {LoginService} from "../../login/login.service";
import {Actions, Effect} from "@ngrx/effects";
import {LOGIN_USER_ACTION, LoginUserAction} from "../actions";
import {Credentials} from "../../shared";
import "rxjs/add/operator/switchMap";
import "rxjs/add/operator/map";
import {LoginUserActionSuccess} from "../actions/actions";

@Injectable()
export class LoginServiceEffects {

    @Effect() login$ = this._action$
        .ofType(LOGIN_USER_ACTION)
        .map((action: LoginUserAction) => action.payload)
        .switchMap((credentials: Credentials) => this._loginService.login(credentials))
        .map((isLogged: boolean) => new LoginUserActionSuccess(isLogged));


    constructor(private _action$: Actions, private _loginService: LoginService) {

    }
}