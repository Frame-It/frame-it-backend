package com.org.framelt.user.adapter.out

import org.springframework.data.repository.Repository

interface UserJpaRepository : Repository<UserJpaEntity, Long> {
    fun save(user: UserJpaEntity): UserJpaEntity

    fun findById(id: Long): UserJpaEntity
}
