package com.platform.ots.adminservice.filter

import com.platform.ots.adminservice.constant.Constants
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.concurrent.TimeUnit.SECONDS


@Component
class CachingHttpHeaderFilter : WebFilter {


    private val dateTime = System.currentTimeMillis()
    private val cacheTimeToLive: Long = SECONDS.toMillis(5)


    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        exchange.response.headers.setDate(Constants.LAST_MODIFIED, dateTime)
        exchange.response.headers.setDate(Constants.EXPIRES, this.cacheTimeToLive + dateTime)
        exchange.response.headers.set(Constants.PRAGMA, Constants.NO_CACHE)
        exchange.response.headers.set(Constants.CACHE_CONTROL, "max-age=${this.cacheTimeToLive}, public")
        return chain.filter(exchange)
    }
}