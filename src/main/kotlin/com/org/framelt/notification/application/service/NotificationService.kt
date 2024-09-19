package com.org.framelt.notification.application.service

import com.org.framelt.notification.adapter.`in`.*
import com.org.framelt.notification.application.port.`in`.NotificationDeleteUseCase
import com.org.framelt.notification.application.port.`in`.NotificationMarkAsReadUseCase
import com.org.framelt.notification.application.port.`in`.NotificationQueryUseCase
import com.org.framelt.notification.application.port.`in`.NotificationSendPort
import com.org.framelt.notification.application.port.out.NotificationCommendPort
import com.org.framelt.notification.application.port.out.NotificationReadPort
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationCommendPort: NotificationCommendPort,
    private val notificationReadPort: NotificationReadPort,
    private val notificationSendPort: NotificationSendPort,
    private val userQueryPort: UserQueryPort,
) : NotificationDeleteUseCase, NotificationMarkAsReadUseCase, NotificationQueryUseCase {

    override fun deleteNotification(command: NotificationDeleteCommand) {
        val user = userQueryPort.readById(command.userId)
        notificationCommendPort.deleteById(command.notificationId)
    }

    override fun markAllAsRead(command: MarkAllAsReadCommand) {
        val user = userQueryPort.readById(command.userId)
        notificationCommendPort.updateAll(command.userId)
    }

    override fun getNotificationStatus(command: NotificationReadAllCommand): List<NotificationResponse> {
        val user = userQueryPort.readById(command.userId)
        val notification = notificationReadPort.findAllByReceiverId(command.userId)
        return notification.map { NotificationResponse(it.id, it.title, it.content, it.sendTime, it.isRead) }.toList()
    }

    override fun changeNotificationStatus(command: NotificationReadCommand) {
        val user = userQueryPort.readById(command.userId)
        notificationCommendPort.updateById(command.id)
    }

    @EventListener
    @Async
    fun sendTo(letter: NotificationLetter) {
        notificationSendPort.sendTo(letter)
    }
}
