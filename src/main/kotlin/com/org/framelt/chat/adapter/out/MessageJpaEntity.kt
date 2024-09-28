package com.org.framelt.chat.adapter.out

import com.org.framelt.chat.domain.Message
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.toDomain
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "messages")
data class MessageJpaEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "chat_id")
    val chat: ChatJpaEntity,

    @ManyToOne
    @JoinColumn(name = "sender_id")
    val sender: UserJpaEntity,

    val timeScript: LocalDateTime,
    val content: String,
    val isRead: Boolean = false
) {
    fun toDomain(): Message {
        return Message(
            id = this.id,
            sender = this.sender.toDomain(),
            timeScript = this.timeScript,
            content = this.content
        )
    }
}
