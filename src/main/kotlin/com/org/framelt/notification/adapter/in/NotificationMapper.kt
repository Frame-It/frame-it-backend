package com.org.framelt.notification.adapter.`in`

import com.org.framelt.notification.domain.Notification

class NotificationMapper {

    companion object {
        fun toStatusResponse(notification: Notification): NotificationStatusResponse {
            return NotificationStatusResponse(
                id = notification.id,
                isRead = notification.isRead
            )
        }
    }
}
