package com.platform.ots.adminservice.config

import com.platform.ots.adminservice.filter.CorsFilter
import com.platform.ots.adminservice.security.*
import org.springframework.beans.factory.BeanInitializationException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder.AUTHENTICATION
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityModuleConfiguration(val corsFilter: CorsFilter,
                                  val userDetailsService: ReactiveUserDetailsService,
                                  val amsAuthenticationConverter: AmsAuthenticationConverter) {


    @Bean
    fun passwordEncoder(): PasswordEncoder = BcryptPasswordEncoderWrapper(BCryptPasswordEncoder())

    @Bean
    fun authenticationManager(passwordEncoder: PasswordEncoder) = AmsAuthenticationManager(passwordEncoder, userDetailsService)


    @Bean
    fun amsAjaxAuthenticationSuccessHandler(): ServerAuthenticationSuccessHandler = AmsAjaxAuthenticationSuccessHandler()

    @Bean
    fun amsAjaxAuthenticationFailureHandler(): ServerAuthenticationFailureHandler = AmsAjaxAuthenticationFailureHandler()

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity,
                                  authenticationManager: ReactiveAuthenticationManager,
                                  webFilter: AuthenticationWebFilter): SecurityWebFilterChain {
        http.formLogin().disable()
        http.httpBasic().disable()
        http.csrf().disable()
        http.headers().frameOptions().disable()

        return http.authenticationManager(authenticationManager)
                .addFilterAt(corsFilter, AUTHENTICATION).exceptionHandling()
                .and()
                .addFilterAt(webFilter, AUTHENTICATION).exceptionHandling()
                .and()
                .authorizeExchange()
                .pathMatchers("/api/v1/register").permitAll()
                .pathMatchers("/api/v1/activate").permitAll()
                .pathMatchers("/api/v1/authenticate").permitAll()
                .pathMatchers("/api/v1/account/reset-password/init").permitAll()
                .pathMatchers("/api/v1/account/reset-password/finish").permitAll()
                .pathMatchers("/api/v1/profile-info").permitAll()
                .pathMatchers("/api/v1/users").authenticated()
                .and()
                .build()
    }


    @Bean
    fun apiAuthenticationWebFilter(authenticationManager: ReactiveAuthenticationManager,
                                   serverSecurityContextRepository: ServerSecurityContextRepository): AuthenticationWebFilter {
        try {
            val apiAuthenticationWebFilter = AuthenticationWebFilter(authenticationManager)
            apiAuthenticationWebFilter.setAuthenticationFailureHandler(amsAjaxAuthenticationFailureHandler())
            apiAuthenticationWebFilter.setRequiresAuthenticationMatcher(PathPatternParserServerWebExchangeMatcher("/api/**"))
            apiAuthenticationWebFilter.setSecurityContextRepository(serverSecurityContextRepository)
            apiAuthenticationWebFilter.setAuthenticationConverter(amsAuthenticationConverter)
            return apiAuthenticationWebFilter
        } catch (e: Exception) {
            throw BeanInitializationException("Could not initialize AuthenticationWebFilter apiAuthenticationWebFilter.", e)
        }

    }

    @Bean
    fun securityContextRepository(): WebSessionServerSecurityContextRepository {
        return WebSessionServerSecurityContextRepository()
    }

}