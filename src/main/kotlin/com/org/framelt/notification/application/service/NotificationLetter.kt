package com.org.framelt.notification.application.service

import com.org.framelt.notification.domain.NotificationEventType
import com.org.framelt.notification.domain.NotificationReceiverType
import com.org.framelt.user.domain.User
import java.time.LocalDateTime

data class NotificationLetter(
    val sender: User,
    val receiver: User,
    val title: String,
    val content: String,
    val id: Long,
    val receiverType: NotificationReceiverType,
    val eventType: NotificationEventType,
    val time: LocalDateTime,
)
