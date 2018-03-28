import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {AsyncLocalStorage} from "angular-async-local-storage";
import {Observable} from "rxjs/Observable";
import {Credentials} from "../vm/credentials";
import {Token} from "../vm/token";
import "rxjs/add/operator/switchMap";

@Injectable()
export class AuthService {

    constructor(private _httpClient: HttpClient,
                private _localStorage: AsyncLocalStorage) {

    }


    getToken(): Observable<string> {
        return this._localStorage.getItem('authenticationToken')
    }

    login(credentials: Credentials): Observable<boolean> {
        return this._httpClient.post('/api/v1/authenticate', credentials)
            .switchMap((data: Token) => this.storeAuthToken(data.token))
    }


    loginWithToken(authToken: String): Observable<boolean> {
        return authToken ? this.storeAuthToken(authToken) : Observable.empty()
    }

    storeAuthToken(authToken: String): Observable<boolean> {
        return this._localStorage.setItem('authenticationToken', authToken)
    }

    logout(): Observable<boolean> {
        return this._localStorage.removeItem('authenticationToken')
    }

}