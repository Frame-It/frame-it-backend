package com.org.framelt.notification.domain

import com.org.framelt.project.domain.Status
import com.org.framelt.user.domain.User
import java.time.LocalDateTime

class Notification(
    val id: Long = 0L,
    val sender: User,
    val receiver: User,
    val title: String,
    val content: String,
    val sendTime: LocalDateTime,
    val resourcesId: Long,
    val projectStatus: Status?,
    val isHost: Boolean?,
    val eventType: NotificationEventType,
    var isRead: Boolean,
) {
    fun markAsRead() {
        this.isRead = true
    }
}
