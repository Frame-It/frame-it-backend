package com.org.framelt.notification.adapter.out

import com.org.framelt.notification.domain.Notification
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class NotificationRepository(
    private val notificationJpaRepository: NotificationJpaRepository,
) {

    fun findById(id: Long): Optional<Notification> {
        val notificationEntity = notificationJpaRepository.findById(id)
        return notificationEntity.map { it.toDomain() }
    }

    fun deleteById(id: Long) {
        notificationJpaRepository.deleteById(id)
    }

    fun findAllByReceiverId(receiverId: Long): List<Notification> {
        val notificationEntities = notificationJpaRepository.findAllByReceiverId(receiverId)
        return notificationEntities.map { it.toDomain() }
    }

    fun updateAll(id: Long) {
        notificationJpaRepository.markAllAsReadByReceiverId(id)
    }
}
