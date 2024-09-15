package com.org.framelt.notification.application.port.`in`

import com.org.framelt.notification.adapter.`in`.MarkAllAsReadCommand
import com.org.framelt.notification.adapter.`in`.NotificationDeleteCommand
import com.org.framelt.notification.adapter.`in`.NotificationReadCommand
import com.org.framelt.notification.adapter.`in`.NotificationResponse

interface NotificationDeleteUseCase {
    fun deleteNotification(notificationId: NotificationDeleteCommand)
}

interface NotificationMarkAsReadUseCase {
    fun markAllAsRead(userId: MarkAllAsReadCommand)
}

interface NotificationQueryUseCase {
    fun getNotificationStatus(notificationId: NotificationReadCommand): List<NotificationResponse>
}
