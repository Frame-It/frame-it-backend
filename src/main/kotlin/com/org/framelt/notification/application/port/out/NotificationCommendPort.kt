package com.org.framelt.notification.application.port.out

import com.org.framelt.notification.domain.Notification

interface NotificationCommendPort {
    fun save(notification: Notification)
    fun deleteById(notificationId: Long)
    fun updateAll(userId: Long)
}
