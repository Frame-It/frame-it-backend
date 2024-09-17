package com.org.framelt.notification.adapter.out

import com.org.framelt.notification.domain.Notification
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.toDomain
import jakarta.persistence.*
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

    @Column(name = "is_read", nullable = false)
    var isRead: Boolean
) {
    fun toDomain(): Notification {
        return Notification(
            id = this.id ?: 0L,
            sender = this.sender.toDomain(),
            receiver = this.receiver.toDomain(),
            title = this.title,
            content = this.content,
            sendTime = this.sendTime,
            isRead = this.isRead
        )
    }
}
