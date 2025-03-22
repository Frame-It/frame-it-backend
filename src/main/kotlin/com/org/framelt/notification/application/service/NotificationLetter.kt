package com.org.framelt.notification.application.service

import com.org.framelt.notification.domain.NotificationEventType
import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.Status
import com.org.framelt.user.domain.User
import java.time.LocalDateTime

data class NotificationLetter(
    val sender: User,
    val receiver: User,
    val title: String,
    val content: String,
    val id: Long,
    val projectStatus: Status?,
    val isHost: Boolean?,
    val eventType: NotificationEventType,
    val time: LocalDateTime,

    // 메일 알림 전송을 위해 임시 추가
    val project: Project? = null,
)
