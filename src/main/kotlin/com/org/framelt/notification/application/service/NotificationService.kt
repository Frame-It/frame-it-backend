package com.org.framelt.notification.application.service

import com.org.framelt.notification.adapter.`in`.MarkAllAsReadCommand
import com.org.framelt.notification.adapter.`in`.NotificationDeleteCommand
import com.org.framelt.notification.adapter.`in`.NotificationStatusCommand
import com.org.framelt.notification.adapter.`in`.NotificationStatusResponse
import com.org.framelt.notification.adapter.out.NotificationRepository
import com.org.framelt.notification.application.port.`in`.NotificationDeleteUseCase
import com.org.framelt.notification.application.port.`in`.NotificationMarkAsReadUseCase
import com.org.framelt.notification.application.port.`in`.NotificationQueryUseCase
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository
) : NotificationDeleteUseCase, NotificationMarkAsReadUseCase, NotificationQueryUseCase {

    override fun deleteNotification(command: NotificationDeleteCommand) {
        notificationRepository.deleteById(command.notificationId)
    }

    override fun markAllAsRead(command: MarkAllAsReadCommand) {
        notificationRepository.updateAll(command.userId)
    }

    override fun getNotificationStatus(command: NotificationStatusCommand): NotificationStatusResponse {
        val notification = notificationRepository.findById(command.notificationId)
            .orElseThrow{ RuntimeException("Notification not found")}
        return NotificationStatusResponse(command.notificationId, notification.isRead)
    }
}
