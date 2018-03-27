package com.platform.ots.adminservice.web.error_handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.platform.ots.adminservice.web.error.ErrorConstants
import com.platform.ots.adminservice.web.error.IncorrectPasswordException
import io.jsonwebtoken.SignatureException
import mu.KLogger
import mu.KotlinLogging
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import org.zalando.problem.Problem
import org.zalando.problem.Status
import org.zalando.problem.ThrowableProblem
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.net.URI


@Component
@Order(-2)
class AmsWebFluxExceptionHandler(val objectMapper: ObjectMapper) : WebExceptionHandler {

    val log: KLogger = KotlinLogging.logger { }

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        log.info("Expected Error")
        when (ex) {
            is UsernameNotFoundException -> return handleException(exchange, ex, ErrorConstants.NOT_FOUND)
            is IncorrectPasswordException -> return handleException(exchange, ex, ErrorConstants.FORBIDDEN)
            is SignatureException -> return handleException(exchange, ex, ErrorConstants.FORBIDDEN)
        }
        return Mono.empty()
    }

    private fun handleException(exchange: ServerWebExchange,
                                ex: Exception,
                                httpStatus: String): Mono<Void> {
        exchange.response.statusCode = HttpStatus.valueOf(httpStatus)
        exchange.response.headers["Content-Type"] = APPLICATION_JSON_VALUE
        val build: ThrowableProblem = Problem.builder()
                .withType(URI.create(exchange.request.path.value()))
                .withTitle(ex.message)
                .withStatus(Status.valueOf(httpStatus))
                .build()
        val buffer = exchange.response.bufferFactory().wrap(objectMapper.writeValueAsBytes(build))
        return exchange.response.writeWith(buffer.toMono())
    }

}