package com.org.framelt.user.adapter.out.persistence

import org.springframework.data.repository.Repository

interface QuitUserJpaRepository : Repository<QuitUserJpaEntity, Long> {
    fun save(quitUserJpaEntity: QuitUserJpaEntity)
}
