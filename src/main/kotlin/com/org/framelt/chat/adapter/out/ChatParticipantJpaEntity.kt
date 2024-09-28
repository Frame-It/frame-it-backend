package com.org.framelt.chat.adapter.out

import com.org.framelt.chat.domain.Participant
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.toDomain
import jakarta.persistence.*

@Entity
@Table(name = "chat_participants")
data class ChatParticipantJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    val chat: ChatJpaEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserJpaEntity,

    @Column(name = "unread_count", nullable = false)
    var unreadCount: Int = 0,
) {
    fun toDomain(): Participant {
        return Participant(
            user = user.toDomain(),
            unreadCount = unreadCount
        )
    }

    fun readMessage() {
        unreadCount = 0
    }

    companion object {
        fun from(participant: Participant, chat: ChatJpaEntity): ChatParticipantJpaEntity {
            return ChatParticipantJpaEntity(
                chat = chat,
                user = UserJpaEntity.fromDomain(participant.user),
                unreadCount = participant.unreadCount
            )
        }
    }
}
