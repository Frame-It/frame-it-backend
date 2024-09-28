package com.org.framelt.chat.adapter.out

import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatParticipantRepository : JpaRepository<ChatParticipantJpaEntity, Long> {
    fun findByChatIdAndUserId(chatId: Long, userId: Long): ChatParticipantJpaEntity?
}
