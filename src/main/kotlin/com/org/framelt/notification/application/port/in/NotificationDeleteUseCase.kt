package com.org.framelt.notification.application.port.`in`

import com.org.framelt.notification.adapter.`in`.*

interface NotificationDeleteUseCase {
    fun deleteNotification(notificationId: NotificationDeleteCommand)
}

interface NotificationMarkAsReadUseCase {
    fun markAllAsRead(userId: MarkAllAsReadCommand)
}

interface NotificationQueryUseCase {
    fun getNotificationStatus(notificationId: NotificationReadAllCommand): List<NotificationResponse>
    fun changeNotificationStatus(command: NotificationReadCommand)
}
