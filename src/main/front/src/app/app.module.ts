import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AuthGuard} from './shared';
import {UserServiceEffects} from "./store/effects/user-service-effects";
import {EffectsModule} from "@ngrx/effects";
import {StoreModule} from "@ngrx/store";
import {reducers} from "./store/reducers/index";
import {INITIAL_APPLICATION_STATE} from "./store/appication-state";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";
import {UserService} from "./layout/users/user.service";
import {RouterStateSerializer, StoreRouterConnectingModule} from "@ngrx/router-store";
import {CustomSerializer} from "./store/custom-serializer";
import {RouterServiceEffects} from "./store/effects/router-service-effects";
import {ToastModule, ToastOptions} from "ng2-toastr";
import {HttpModule} from "@angular/http";
import {WeatherService} from "./layout/weather/weather.service";
import {WeatherServiceEffects} from "./store/effects/weather-service-effects";

export function createTranslateLoader(http: HttpClient) {
    return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}


export class CustomOption extends ToastOptions {
    animate = 'flyRight';
    showCloseButton = true;
    positionClass = 'toast-bottom-right'
}

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        ToastModule.forRoot(),
        HttpClientModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: createTranslateLoader,
                deps: [HttpClient]
            }
        }),
        AppRoutingModule,
        StoreModule.forRoot(reducers, {
            initialState: INITIAL_APPLICATION_STATE
        }),
        EffectsModule.forRoot([
            UserServiceEffects,
            WeatherServiceEffects,
            RouterServiceEffects
        ]),
        StoreRouterConnectingModule.forRoot({
            stateKey: 'router'
        }),
        StoreDevtoolsModule.instrument(),
        HttpModule
    ],
    declarations: [AppComponent],
    providers: [AuthGuard, UserService, WeatherService,
        {
            provide: RouterStateSerializer,
            useClass: CustomSerializer
        },
        {
            provide: ToastOptions,
            useClass: CustomOption
        },
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
