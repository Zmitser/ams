package com.platform.ots.adminservice.web.handler

import com.platform.ots.adminservice.web.service.AuditEventService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Component
class AuditHandler(val auditEventService: AuditEventService) {

    val formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE

    fun findAll(serverRequest: ServerRequest): Mono<ServerResponse> {
        val sort: String = serverRequest.queryParam("_sort").orElse("id")
        val order: Sort.Direction = serverRequest.queryParam("_order").map { Sort.Direction.valueOf(it) }.orElse(Sort.Direction.ASC)
        val page: Int = serverRequest.queryParam("_page").map { it.toInt().minus(1) }.orElse(0)
        val limit: Int = serverRequest.queryParam("_limit").map { it.toInt() }.orElse(5)
        return ok().body(auditEventService.find(sort, order, page, limit))
    }

    fun findOne(request: ServerRequest): Mono<ServerResponse> {
        return ok().body(auditEventService.find(request.pathVariable("id").toLong()))
    }

    fun findAllBetweenDates(serverRequest: ServerRequest): Mono<ServerResponse> {
        val sort: String = serverRequest.queryParam("_sort").orElse("id")
        val order: Sort.Direction = serverRequest.queryParam("_order").map { Sort.Direction.valueOf(it) }.orElse(Sort.Direction.ASC)
        val page: Int = serverRequest.queryParam("_page").map { it.toInt().minus(1) }.orElse(0)
        val limit: Int = serverRequest.queryParam("_limit").map { it.toInt() }.orElse(5)
        val fromDate: Instant = LocalDate.parse(serverRequest.queryParam("fromDate").orElse(""), DateTimeFormatter.ISO_DATE).atStartOfDay(ZoneId.systemDefault()).toInstant()
        val toDate: Instant = LocalDate.parse(serverRequest.queryParam("toDate").orElse(""), DateTimeFormatter.ISO_DATE).atStartOfDay(ZoneId.systemDefault()).toInstant()
        return ok().body(auditEventService.find(sort, order, page, limit, fromDate, toDate))
    }

}