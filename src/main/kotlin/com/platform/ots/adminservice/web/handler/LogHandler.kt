package com.platform.ots.adminservice.web.handler

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import com.codahale.metrics.annotation.Timed
import com.platform.ots.adminservice.web.response.LoggerResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono

@Component
class LogHandler {

    @Timed
    fun list(serverRequest: ServerRequest): Mono<ServerResponse> {
        val context: LoggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
        return ok().body(context.loggerList.map { LoggerResponse(it) }.toFlux())
    }

    @Timed
    fun changeLevel(serverRequest: ServerRequest): Mono<ServerResponse> {
        val context: LoggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
        return serverRequest.bodyToMono(LoggerResponse::class.java)
                .map {
                    context.getLogger(it.name).level = Level.valueOf(it.level)
                }
                .flatMap {
                    status(HttpStatus.NO_CONTENT).body(it.toMono())
                }
    }
}