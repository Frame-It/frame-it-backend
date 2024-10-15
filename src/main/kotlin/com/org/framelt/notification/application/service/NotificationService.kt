package com.org.framelt.notification.application.service

import com.org.framelt.notification.adapter.`in`.MarkAllAsReadCommand
import com.org.framelt.notification.adapter.`in`.NotificationDeleteCommand
import com.org.framelt.notification.adapter.`in`.NotificationReadCommand
import com.org.framelt.notification.adapter.`in`.NotificationResponse
import com.org.framelt.notification.application.port.`in`.NotificationDeleteUseCase
import com.org.framelt.notification.application.port.`in`.NotificationMarkAsReadUseCase
import com.org.framelt.notification.application.port.`in`.NotificationQueryUseCase
import com.org.framelt.notification.application.port.`in`.NotificationSendPort
import com.org.framelt.notification.application.port.out.NotificationCommendPort
import com.org.framelt.notification.application.port.out.NotificationReadPort
import com.org.framelt.notification.domain.Notification
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import com.org.framelt.user.domain.User
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationService(
    private val notificationCommendPort: NotificationCommendPort,
    private val notificationReadPort: NotificationReadPort,
    private val notificationSendPort: NotificationSendPort,
    private val userQueryPort: UserQueryPort,
) : NotificationDeleteUseCase, NotificationMarkAsReadUseCase, NotificationQueryUseCase {

    private fun addNotification(sender: User, receiver: User, title: String, content: String) {
        val notification = Notification(
            id = 0L,
            sender = sender,
            receiver = receiver,
            title = title,
            content = content,
            sendTime = LocalDateTime.now(),
            isRead = false
        )
        notificationCommendPort.save(notification)
    }

    override fun deleteNotification(command: NotificationDeleteCommand) {
        val user = userQueryPort.readById(command.userId)
        notificationCommendPort.deleteById(command.notificationId)
    }

    override fun markAllAsRead(command: MarkAllAsReadCommand) {
        val user = userQueryPort.readById(command.userId)
        notificationCommendPort.updateAll(command.userId)
    }

    override fun getNotificationStatus(command: NotificationReadCommand): List<NotificationResponse> {
        val user = userQueryPort.readById(command.userId)
        val notification = notificationReadPort.findAllByReceiverId(command.userId)
        return notification.map { NotificationResponse(it.id, it.title, it.content, it.sendTime, it.isRead) }.toList()
    }

    @EventListener
    @Async
    fun sendTo(letter: NotificationLetter) {
        addNotification(letter.sender, letter.receiver, letter.title, letter.content)
        notificationSendPort.sendTo(letter)
    }
}
