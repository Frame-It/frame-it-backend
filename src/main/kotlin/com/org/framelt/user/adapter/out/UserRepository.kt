package com.org.framelt.user.adapter.out

import com.org.framelt.user.application.port.out.UserReadPort
import com.org.framelt.user.domain.Concept
import com.org.framelt.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val userJpaRepository: UserJpaRepository,
) : UserReadPort {

    override fun readById(id: Long): User {
        val userEntity = userJpaRepository.findById(id)
            .orElseThrow { RuntimeException("User not found with id: $id") }
        return toDomain(userEntity)
    }

    override fun readByIds(ids: List<Long>): List<User> {
        val userEntities = userJpaRepository.findAllById(ids)
        return userEntities.map { toDomain(it) }
    }

    private fun toDomain(userEntity: UserJpaEntity): User {
        return User(
            id = userEntity.id,
            name = userEntity.name,
            nickname = userEntity.nickname,
            profileImageUrl = userEntity.profileImageUrl,
            bio = userEntity.bio,
            identity = userEntity.identity,
            career = userEntity.career,
            shootingConcepts = userEntity.shootingConcepts.map { Concept.SNAP },
            notificationsEnabled = userEntity.notificationsEnabled,
            deviseToken = userEntity.deviceToken
        )
    }
}
