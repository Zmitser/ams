import {Credentials} from "../shared/vm/credentials";

export interface AuthState {
    isLogged: boolean
    credentials: Credentials
}

export const INITIAL_AUTH_STATE: AuthState = {
    isLogged: false,
    credentials: new Credentials()
};