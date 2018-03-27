package com.platform.ots.adminservice.web.router

import com.platform.ots.adminservice.web.handler.AuditHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class AuditRouter(val auditHandler: AuditHandler) {

    @Bean
    fun auditApis(): RouterFunction<ServerResponse> = router {
        (accept(APPLICATION_JSON) and "/api/v1/audits").nest {
            GET("", auditHandler::findAll)
            GET("/{id}", auditHandler::findOne)
            GET("/slice", auditHandler::findAllBetweenDates)
        }
    }


}