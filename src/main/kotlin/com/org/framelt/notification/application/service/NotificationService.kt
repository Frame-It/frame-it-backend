package com.org.framelt.notification.application.service

import com.org.framelt.notification.adapter.`in`.MarkAllAsReadCommand
import com.org.framelt.notification.adapter.`in`.NotificationDeleteCommand
import com.org.framelt.notification.adapter.`in`.NotificationReadCommand
import com.org.framelt.notification.adapter.`in`.NotificationResponse
import com.org.framelt.notification.application.port.`in`.NotificationDeleteUseCase
import com.org.framelt.notification.application.port.`in`.NotificationMarkAsReadUseCase
import com.org.framelt.notification.application.port.`in`.NotificationQueryUseCase
import com.org.framelt.notification.application.port.out.NotificationCommendPort
import com.org.framelt.notification.application.port.out.NotificationReadPort
import com.org.framelt.user.application.port.out.UserReadPort
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationCommendPort: NotificationCommendPort,
    private val notificationReadPort: NotificationReadPort,
    private val userReadPort: UserReadPort,
) : NotificationDeleteUseCase, NotificationMarkAsReadUseCase, NotificationQueryUseCase {

    override fun deleteNotification(command: NotificationDeleteCommand) {
        val user = userReadPort.readById(command.userId)
        notificationCommendPort.deleteById(command.notificationId)
    }

    override fun markAllAsRead(command: MarkAllAsReadCommand) {
        val user = userReadPort.readById(command.userId)
        notificationCommendPort.updateAll(command.userId)
    }

    override fun getNotificationStatus(command: NotificationReadCommand): List<NotificationResponse> {
        val user = userReadPort.readById(command.userId)
        val notification = notificationReadPort.findByReceiverId(command.userId)
        return notification.map { NotificationResponse(it.id, it.title, it.content, it.sendTime, it.isRead) }.toList()
    }
}
