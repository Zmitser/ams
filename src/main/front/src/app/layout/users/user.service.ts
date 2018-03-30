import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {User} from "../../shared/models/user";
import "rxjs/add/observable/of";
import "rxjs/add/operator/switchMap";
import "rxjs/add/operator/map";
import {ServerDataSource} from "ng2-smart-table";
import {Http} from '@angular/http';

@Injectable()
export class UserService {

    constructor(private _httpClient: HttpClient,
                private _http: Http) {

    }

    findAll(): Observable<User[]> {
        return this._httpClient.get<User[]>('/api/v1/users')
    }

    createServerDataSource(): Observable<ServerDataSource> {
        const serverDataSource = new ServerDataSource(this._http, {
            endPoint: '/api/v1/users/page',
            dataKey: 'data',
            totalKey: 'total'
        });
        return Observable.of(serverDataSource);
    }

    delete(id: number): Observable<number> {
        return this._httpClient.delete<number>(`/api/v1/users/${id}`)
    }

    findOne(id: number): Observable<User> {
        return this._httpClient.get<User>(`/api/v1/users/${id}`)
    }

    createEmptyUser(): Observable<User> {
        return Observable.of(new User())
    }

    save(user: User): Observable<User> {
        console.log(`From save`, user);
        return this._httpClient.post<User>('/api/v1/users', user)
    }
}
