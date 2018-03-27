package com.platform.ots.adminservice.web.router

import com.platform.ots.adminservice.web.handler.LogHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class LogRouter(val logHandler: LogHandler) {

    @Bean
    fun logApis(): RouterFunction<ServerResponse> = router {
        (accept(APPLICATION_JSON) and "/api/v1/logs").nest {
            GET("", logHandler::list)
            PUT("", logHandler::changeLevel)
        }
    }
}