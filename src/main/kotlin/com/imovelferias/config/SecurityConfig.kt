package com.imovelferias.config

import com.imovelferias.utils.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableReactiveMethodSecurity
class SecurityConfig(private val jwtFilter: JwtFilter) {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        corsConfiguration.allowedOrigins = listOf("*")
        corsConfiguration.allowedHeaders = listOf("*")
        http
            .cors {
                it.configurationSource { corsConfiguration }
            }
            .authorizeExchange { auth ->
                auth
                    .pathMatchers(
                        "/api/v1/announce/all",
                        "/api/v1/auth/**",
                    ).permitAll()
                    .anyExchange().authenticated()
            }
            .addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .csrf { it.disable() }
            .httpBasic { it.disable() }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

}