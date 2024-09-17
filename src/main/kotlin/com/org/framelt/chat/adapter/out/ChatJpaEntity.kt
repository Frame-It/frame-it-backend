package com.org.framelt.chat.adapter.out

import com.org.framelt.chat.domain.Chatting
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.toDomain
import jakarta.persistence.*

@Entity
@Table(name = "chats")
data class ChatJpaEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToMany
    @JoinTable(
        name = "chat_participants",
        joinColumns = [JoinColumn(name = "chat_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val participants: List<UserJpaEntity>,

    @OneToMany(mappedBy = "chat", cascade = [CascadeType.ALL], orphanRemoval = true)
    private var messages: MutableList<MessageJpaEntity> = mutableListOf(),
) {

    constructor(participants: List<UserJpaEntity>) : this(0L, participants)

    fun updateMessage(messageJpaEntities: MutableList<MessageJpaEntity>) {
        this.messages = messageJpaEntities
    }

    fun toDomain(): Chatting {
        return Chatting(
            id = this.id,
            participant = this.participants.map { it.toDomain() },
            messages = this.messages.map { it.toDomain() }.toMutableList()
        )
    }
}
