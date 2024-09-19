package com.org.framelt.notification.adapter.out

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface NotificationJpaRepository : JpaRepository<NotificationJpaEntity, Long> {
    fun findAllByReceiverId(receiverId: Long): List<NotificationJpaEntity>

    @Modifying
    @Query("UPDATE NotificationJpaEntity n SET n.isRead = true WHERE n.receiver.id = :receiverId")
    fun markAllAsReadByReceiverId(@Param("receiverId") receiverId: Long): Int

    @Modifying
    @Query("UPDATE NotificationJpaEntity n SET n.isRead = true WHERE n.id = :id")
    fun markAsReadByReceiverId(@Param("id") id: Long): Int
}
