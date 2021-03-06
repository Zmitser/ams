package com.platform.ots.adminservice.repository.impl

import com.platform.ots.adminservice.domain.User
import com.platform.ots.adminservice.repository.UserRepository
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.justOrEmpty
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono


@Repository
class UserRepositoryImpl(val proxyUserRepository: ProxyUserRepository) : UserRepository {
    override fun saveAll(users: List<User>): Flux<User> = proxyUserRepository.saveAll(users).toFlux()

    override fun findOneByUsernameOrEmail(usernameOrEmail: String?): Mono<User> {
        return justOrEmpty(proxyUserRepository.findOneByUsernameOrEmail(usernameOrEmail))
    }


    private val log = KotlinLogging.logger {}

    override fun findAll(pageable: Pageable): Mono<Page<User>> {
        return proxyUserRepository.findAll(pageable).toMono()
    }

    override fun findOne(id: Long): Mono<User> = proxyUserRepository.findById(id).orElseGet { null }.toMono()

    override fun delete(id: Long): Mono<Long> = id.toMono().doOnSuccess { proxyUserRepository.deleteById(id) }

    override fun save(user: User): Mono<User> {
        log.debug { "Try to save user: $user" }
        return proxyUserRepository.save(user).toMono()
    }

    override fun deleteAll(): Mono<Unit> = proxyUserRepository.deleteAll().toMono()

    override fun findAll(): Flux<User> = proxyUserRepository.findAll().toFlux()


    override fun findOneByUsername(username: String?): Mono<User> {
        return proxyUserRepository.findOneByUsername(username).toMono()
    }

    override fun findOneByEmail(email: String?): Mono<User> {
        return proxyUserRepository.findOneByEmail(email).toMono()
    }

}