package com.platform.ots.adminservice.web.router

import com.platform.ots.adminservice.web.handler.WeatherHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class WeatherRouter(val weatherHandler: WeatherHandler) {

    @Bean
    fun weatherApis(): RouterFunction<ServerResponse> = router {
        (accept(MediaType.APPLICATION_JSON) and "/api/v1/weather").nest {
            GET("", weatherHandler::getWeather)
        }
    }
}