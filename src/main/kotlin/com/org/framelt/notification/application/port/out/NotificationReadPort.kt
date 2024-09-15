package com.org.framelt.notification.application.port.out

import com.org.framelt.notification.domain.Notification

interface NotificationReadPort {
    fun findByReceiverId(userId: Long): List<Notification>
}
