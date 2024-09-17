package com.org.framelt.notification.adapter.out

import com.org.framelt.notification.application.port.out.NotificationCommendPort
import com.org.framelt.notification.application.port.out.NotificationReadPort
import com.org.framelt.notification.domain.Notification
import org.springframework.stereotype.Repository

@Repository
class NotificationRepository(
    private val notificationJpaRepository: NotificationJpaRepository,
) : NotificationReadPort, NotificationCommendPort {

    override fun deleteById(id: Long) {
        notificationJpaRepository.deleteById(id)
    }

    override fun findAllByReceiverId(receiverId: Long): List<Notification> {
        val notificationEntities = notificationJpaRepository.findAllByReceiverId(receiverId)
        return notificationEntities.map { it.toDomain() }
    }

    override fun updateAll(id: Long) {
        notificationJpaRepository.markAllAsReadByReceiverId(id)
    }
}
