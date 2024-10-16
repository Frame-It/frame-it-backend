package com.org.framelt.chat.adapter.out

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ChatJpaRepository : JpaRepository<ChatJpaEntity, Long> {
    @Query(
        """
        SELECT c FROM ChatJpaEntity c 
        JOIN c.participants p1 
        JOIN c.participants p2 
        WHERE p1.user.id = :userId1 AND p2.user.id = :userId2
    """,
    )
    fun findChatBetweenUsers(
        userId1: Long,
        userId2: Long,
    ): ChatJpaEntity?

    fun findAllByParticipantsUserId(userId: Long): List<ChatJpaEntity>
}
