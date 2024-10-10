package com.org.framelt.chat.adapter.out

import org.springframework.data.jpa.repository.JpaRepository

interface ChatJpaRepository : JpaRepository<ChatJpaEntity, Long> {
    fun findAllByParticipantsId(userId: Long): List<ChatJpaEntity>
}
