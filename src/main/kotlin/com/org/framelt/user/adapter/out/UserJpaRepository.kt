package com.org.framelt.user.adapter.out

import org.springframework.data.repository.Repository

interface UserJpaRepository : Repository<UserJpaEntity, Long> {
    fun findById(id: Long): UserJpaEntity
}
