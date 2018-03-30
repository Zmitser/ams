import {Injectable} from '@angular/core';
import {CanActivate} from '@angular/router';
import {ApplicationState} from "../../store/appication-state";
import {Store} from "@ngrx/store";
import {AuthService} from "../services/auth.service";

@Injectable()
export class AuthGuard implements CanActivate {
    isLogged: boolean = true;

    constructor(private _authService: AuthService, private _store: Store<ApplicationState>) {
    }

    canActivate() {
        return this.isLogged
    }
}
