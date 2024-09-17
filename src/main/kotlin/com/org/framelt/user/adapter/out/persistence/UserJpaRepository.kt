package com.org.framelt.user.adapter.out.persistence

import org.springframework.data.repository.Repository

interface UserJpaRepository : Repository<UserJpaEntity, Long> {
    fun save(user: UserJpaEntity): UserJpaEntity

    fun getById(id: Long): UserJpaEntity

    fun findById(id: Long): UserJpaEntity?

    fun existsByNickname(nickname: String): Boolean

    fun findAllByIds(ids: List<Long>): List<UserJpaEntity>
}
