package com.org.framelt.user.adapter.out

import com.org.framelt.user.application.port.out.UserQueryPort
import com.org.framelt.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val userJpaRepository: UserJpaRepository,
) : UserQueryPort {
    override fun readById(id: Long): User {
        val userEntity = userJpaRepository.findById(id)
        return userEntity.toDomain()
    }
}
