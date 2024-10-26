package com.org.framelt.notification.adapter.out

import com.org.framelt.notification.domain.Notification
import com.org.framelt.notification.domain.NotificationEventType
import com.org.framelt.project.domain.Status
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.toDomain
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "notifications")
class NotificationJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    val sender: UserJpaEntity,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    val receiver: UserJpaEntity,
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val content: String,
    @Column(name = "send_time", nullable = false)
    val sendTime: LocalDateTime,
    @Column(nullable = false)
    val resourcesId: Long,
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    val projectStatus: Status?,
    @Column(nullable = true)
    val isHost: Boolean?,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val eventType: NotificationEventType,
    @Column(name = "is_read", nullable = false)
    var isRead: Boolean,
) {
    fun toDomain(): Notification =
        Notification(
            id = this.id ?: 0L,
            sender = this.sender.toDomain(),
            receiver = this.receiver.toDomain(),
            title = this.title,
            content = this.content,
            sendTime = this.sendTime,
            resourcesId = this.resourcesId,
            projectStatus = this.projectStatus,
            isHost = this.isHost,
            eventType = this.eventType,
            isRead = this.isRead,
        )
}
