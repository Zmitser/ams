import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {User} from "../models/user";

@Injectable()
export class UserService {

    constructor(private _httpClient: HttpClient) {

    }

    findAll(): Observable<User[]> {
        return this._httpClient.get<User[]>('/api/v1/users')
    }

    delete(id: number): Observable<number> {
        return this._httpClient.delete<number>(`/api/v1/users/${id}`)
    }
}
