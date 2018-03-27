package com.platform.ots.adminservice.web.router

import com.platform.ots.adminservice.web.handler.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class UserRouter(val userHandler: UserHandler) {

    @Bean
    fun userApis(): RouterFunction<ServerResponse> = router {
        (accept(APPLICATION_JSON) and "/api/v1/users").nest {
            GET("", userHandler::findAll)
            GET("/page", userHandler::findAllPage)
            DELETE("/{id}", userHandler::delete)
            POST("", userHandler::save)
            GET("/{id}", userHandler::findOne)
        }
    }
}