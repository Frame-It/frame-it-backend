package com.org.framelt.notification.adapter.out

import com.org.framelt.notification.application.port.out.NotificationCommendPort
import com.org.framelt.notification.application.port.out.NotificationReadPort
import com.org.framelt.notification.domain.Notification
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import org.springframework.stereotype.Repository

@Repository
class NotificationRepository(
    private val notificationJpaRepository: NotificationJpaRepository,
) : NotificationReadPort, NotificationCommendPort {
    override fun save(notification: Notification) {
        val notificationEntity = NotificationJpaEntity(
            id = 0L,
            sender = UserJpaEntity.fromDomain(notification.sender),
            receiver = UserJpaEntity.fromDomain(notification.receiver),
            title = notification.title,
            content = notification.content,
            sendTime = notification.sendTime,
            isRead = notification.isRead
        )

        notificationJpaRepository.save(notificationEntity)
    }


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
