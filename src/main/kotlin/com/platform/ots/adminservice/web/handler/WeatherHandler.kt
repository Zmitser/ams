package com.platform.ots.adminservice.web.handler

import com.codahale.metrics.annotation.Timed
import com.platform.ots.adminservice.strategy.WeatherService
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono


@Component
class WeatherHandler(val weatherService: WeatherService) {

    @Timed
    fun getWeather(serverRequest: ServerRequest): Mono<ServerResponse> = ok().body(weatherService.getWeather())

}