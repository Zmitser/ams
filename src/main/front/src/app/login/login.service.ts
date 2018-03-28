import {Injectable} from "@angular/core";
import {AuthService} from "../shared/services/auth.service";
import {Observable} from "rxjs/Observable";
import {Credentials} from "../shared/vm/credentials";

@Injectable()
export class LoginService {

    constructor(private _authService: AuthService) {

    }

    login(credentials: Credentials): Observable<boolean> {
        return this._authService.login(credentials)
    }

    loginWithToken(jwt: String): Observable<boolean> {
        return this._authService.loginWithToken(jwt)
    }

    logout(): Observable<boolean> {
        return this._authService.logout()
    }

}