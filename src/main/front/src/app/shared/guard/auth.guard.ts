import {Injectable} from '@angular/core';
import {CanActivate} from '@angular/router';
import {ApplicationState} from "../../store/appication-state";
import {Store} from "@ngrx/store";
import {Go} from "../../store/actions/actions";

@Injectable()
export class AuthGuard implements CanActivate {
    constructor(private _store: Store<ApplicationState>) {
    }
    canActivate() {
        if (localStorage.getItem('authenticationToken')) {
            return true;
        }

        this._store.dispatch(new Go({
            path: ['/login'],
            query: {},
            extras: {}
        }));
        return false;
    }
}
