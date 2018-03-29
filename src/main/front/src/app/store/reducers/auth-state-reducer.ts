import {AuthState} from "../auth-state";
import {LOGIN_USER_ACTION_SUCCESS, LoginUserActionSuccess} from "../actions";

function handleLoginUserActionSuccess(state: AuthState, action: LoginUserActionSuccess) {
    const newState: AuthState = Object.assign({}, state);
    newState.isLogged = action.payload;
    return newState;
}

export function authReducer(state: AuthState, action: any) {
    switch (action.type) {
        case LOGIN_USER_ACTION_SUCCESS:
            return handleLoginUserActionSuccess(state, action);
        default:
            return state;
    }


}