package com.org.framelt.chat.adapter.out

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ChatJpaRepository : JpaRepository<ChatJpaEntity, Long> {
    @Query("SELECT c FROM ChatJpaEntity c JOIN c.participants p WHERE p.user.id = :userId")
    fun findByUserId(@Param("userId") userId: Long): List<ChatJpaEntity>
}
