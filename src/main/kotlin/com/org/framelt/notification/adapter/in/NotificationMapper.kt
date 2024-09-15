package com.org.framelt.notification.adapter.`in`

import com.org.framelt.notification.domain.Notification

class NotificationMapper {

    companion object {
        fun toStatusResponse(notification: Notification): NotificationResponse {
            return NotificationResponse(
                id = notification.id,
                isRead = notification.isRead
            )
        }
    }
}
