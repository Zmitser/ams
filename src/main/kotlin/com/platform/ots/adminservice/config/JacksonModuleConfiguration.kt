package com.platform.ots.adminservice.config

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.afterburner.AfterburnerModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.problem.ProblemModule
import org.zalando.problem.validation.ConstraintViolationProblemModule

@Configuration
class JacksonModuleConfiguration {

    /*
     * Support for Hibernate types in Jackson.
     */
    @Bean
    fun hibernate5Module(): Hibernate5Module = Hibernate5Module()

    /*
     * Jackson Afterburner module to speed up serialization/deserialization.
     */
    @Bean
    fun afterBurnerModule(): AfterburnerModule = AfterburnerModule()

    /*
     * Module for serialization/deserialization of RFC7807 Problem.
     */
    @Bean
    fun problemModule(): ProblemModule = ProblemModule().withStackTraces()

    @Bean
    fun constraintViolationProblemModule(): ConstraintViolationProblemModule = ConstraintViolationProblemModule()


    /*
     * Module for Kotlin support.
     */
    @Bean
    fun kotlinModule(): KotlinModule = KotlinModule()

    /*
     * Module for Java Time support.
     */
    @Bean
    fun javaTimeModule(): JavaTimeModule = JavaTimeModule()
}