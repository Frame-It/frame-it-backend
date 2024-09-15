package com.org.framelt.user.adapter.out.persistence

import org.springframework.data.repository.Repository

interface UserJpaRepository : Repository<UserJpaEntity, Long> {
    fun save(user: UserJpaEntity): UserJpaEntity

    fun getById(id: Long): UserJpaEntity

    fun findById(id: Long): UserJpaEntity?
}
