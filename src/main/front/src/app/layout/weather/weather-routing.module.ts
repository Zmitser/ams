import {RouterModule, Routes} from "@angular/router";
import {WeatherComponent} from "./weather.component";
import {NgModule} from "@angular/core";

const routes: Routes = [
    {
        path: '', component: WeatherComponent
    }
];


@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class WeatherRoutingModule {
}
