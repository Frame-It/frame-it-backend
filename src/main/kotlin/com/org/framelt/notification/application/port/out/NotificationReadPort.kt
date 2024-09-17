package com.org.framelt.notification.application.port.out

import com.org.framelt.notification.domain.Notification

interface NotificationReadPort {
    fun findAllByReceiverId(userId: Long): List<Notification>
}
