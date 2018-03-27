package com.platform.ots.adminservice.web.handler

import com.codahale.metrics.annotation.Timed
import com.platform.ots.adminservice.web.dto.UserDto
import com.platform.ots.adminservice.web.service.UserService
import mu.KotlinLogging
import org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE
import org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.domain.Sort.Direction.valueOf
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono

@Component
class UserHandler(val userService: UserService) {

    private val log = KotlinLogging.logger {}

    @Timed
    fun findAll(request: ServerRequest): Mono<ServerResponse> = ok().body(userService.findAll())

    @Timed
    fun findAllPage(request: ServerRequest): Mono<ServerResponse> {
        val sort: String = request.queryParam("_sort").orElse("id")
        val order: Direction = request.queryParam("_order").map { valueOf(it) }.orElse(ASC)
        val page: Int = request.queryParam("_page").map { it.toInt().minus(INTEGER_ONE) }.orElse(INTEGER_ZERO)
        val limit = request.queryParam("_limit").map { it.toInt() }.orElse(5)
        return ok().body(userService.findAll(sort, order, page, limit))
    }

    @Timed
    fun delete(request: ServerRequest): Mono<ServerResponse> = ok().body(userService.delete(request.pathVariable("id").toLong()))

    @Timed
    fun save(request: ServerRequest): Mono<ServerResponse> {
        return request.bodyToMono(UserDto::class.java)
                .map {
                    log.debug { "From handler: $it" }
                    userService.save(it)
                }
                .flatMap { status(HttpStatus.CREATED).body(it) }
    }

    @Timed
    fun findOne(request: ServerRequest): Mono<ServerResponse> = ok().body(userService.findOne(request.pathVariable("id").toLong()))

}