import {Component, OnInit} from '@angular/core';
import {routerTransition} from '../router.animations';
import {Observable} from "rxjs/Observable";
import {Credentials} from "../shared";
import {ApplicationState} from "../store/appication-state";
import {select, Store} from "@ngrx/store";
import {LoginUserAction} from "../store/actions";
import {Go} from "../store/actions/actions";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
    animations: [routerTransition()]
})
export class LoginComponent implements OnInit {
    credentials$: Observable<Credentials>;

    constructor(private _store: Store<ApplicationState>) {
        this.credentials$ = this._store.pipe(select(state => state.authState.credentials))
    }

    ngOnInit() {
    }

    login(credentials: Credentials) {
        this._store.dispatch(new LoginUserAction(credentials));
        this._store.dispatch(new Go({
            path: ['/users'],
            query: {},
            extras: {}
        }));
    }
}
