package com.org.framelt.chat.adapter.out

import com.org.framelt.chat.domain.Participant
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.toDomain
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "chat_participants")
data class ChatParticipantJpaEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "chat_id")
    val chat: ChatJpaEntity,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: UserJpaEntity,

    @Column(name = "unread_count", nullable = false)
    var unreadCount: Int = 0,
    @Column(name = "last_message_time", nullable = true)
    var lastMessageTime: LocalDateTime? = null,
) {
    fun toDomain(): Participant {
        return Participant(
            id = this.id,
            user = this.user.toDomain(),
            unreadCount = this.unreadCount
        )
    }
}


