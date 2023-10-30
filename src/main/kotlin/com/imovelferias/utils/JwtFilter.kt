package com.imovelferias.utils

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono


@Component
class JwtFilter(private val jwtTokenProvider: JwtTokenProvider) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request = exchange.request
        val tokens = jwtTokenProvider.resolveToken(request)

        return if (tokens != null && jwtTokenProvider.validateToken(tokens)) {
            val auth: Authentication = jwtTokenProvider.getAuthentication(tokens)
            chain.filter(exchange)
                .contextWrite(
                    ReactiveSecurityContextHolder.withAuthentication(auth)
                )
        } else {
            chain.filter(exchange)
        }
    }
}