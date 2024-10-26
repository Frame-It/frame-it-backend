package com.org.framelt.chat.adapter.out

import com.org.framelt.chat.domain.Chatting
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "chats")
data class ChatJpaEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @OneToMany(mappedBy = "chat", cascade = [CascadeType.ALL], orphanRemoval = true)
    val participants: MutableList<ChatParticipantJpaEntity> = mutableListOf(),
    @OneToMany(mappedBy = "chat", cascade = [CascadeType.ALL], orphanRemoval = true)
    private var messages: MutableList<MessageJpaEntity> = mutableListOf(),
) {
    constructor(participants: List<UserJpaEntity>) : this() {
        participants.forEach { user ->
            this.participants.add(ChatParticipantJpaEntity(chat = this, user = user))
        }
    }

    fun updateMessage(messageJpaEntities: MutableList<MessageJpaEntity>) {
        this.messages = messageJpaEntities
    }

    fun toDomain(): Chatting =
        Chatting(
            id = this.id,
            participants = this.participants.map { it.toDomain() }.toMutableList(),
            messages = this.messages.map { it.toDomain() }.toMutableList(),
        )
}
