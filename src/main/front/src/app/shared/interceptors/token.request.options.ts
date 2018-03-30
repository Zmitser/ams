import {BaseRequestOptions} from "@angular/http";
import {Injectable} from "@angular/core";
import {AuthService} from "../services/auth.service";

@Injectable()
export class TokenRequestOptions extends BaseRequestOptions {


    constructor(private _authService: AuthService) {
        super();
        this._authService.getToken().subscribe(token => {
            this.headers.set('Authorization', `Bearer ${token}`);
        });
    }
}