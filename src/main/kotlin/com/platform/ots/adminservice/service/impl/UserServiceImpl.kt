package com.platform.ots.adminservice.service.impl

import com.platform.ots.adminservice.repository.UserRepository
import com.platform.ots.adminservice.service.UserService
import com.platform.ots.adminservice.service.dto.UserDto
import com.platform.ots.adminservice.service.mapper.UserMapper
import com.platform.ots.adminservice.service.vm.UsersSmartTableResponse
import mu.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserServiceImpl(val userRepository: UserRepository, val userMapper: UserMapper) : UserService {
    private val log = KotlinLogging.logger {}

    override fun findAll(sort: String, order: Sort.Direction, page: Int, limit: Int): Mono<UsersSmartTableResponse> {
        val pageable: PageRequest = PageRequest.of(page, limit, Sort.by(order, sort))
        return userRepository.findAll(pageable).map {
            UsersSmartTableResponse(it.content.map { userMapper.userToUserDto(it) }, it.totalElements)
        }
    }

    override fun findOne(id: Long): Mono<UserDto> {
        return userRepository.findOne(id).map { userMapper.userToUserDto(it) }
    }

    override fun save(userDto: UserDto): Mono<UserDto> {
        val user = userMapper.userDtoToUser(userDto)
        log.debug { "From service: $user" }
        return userRepository.save(user).map { userMapper.userToUserDto(it) }
    }


    override fun delete(id: Long): Mono<Long> = userRepository.delete(id)

    override fun findAll(): Flux<UserDto> = userRepository.findAll().map { userMapper.userToUserDto(it) }
}