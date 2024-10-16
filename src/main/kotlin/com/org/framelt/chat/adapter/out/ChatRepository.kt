package com.org.framelt.chat.adapter.out

import com.org.framelt.chat.application.service.ChatCommendPort
import com.org.framelt.chat.application.service.ChatReadPort
import com.org.framelt.chat.domain.Chatting
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class ChatRepository(
    private val chatJpaRepository: ChatJpaRepository,
) : ChatCommendPort,
    ChatReadPort {
    override fun findChatBetweenUsers(
        firstUserId: Long,
        secondUserId: Long,
    ): Chatting? = chatJpaRepository.findChatBetweenUsers(firstUserId, secondUserId)?.toDomain()

    override fun save(chat: Chatting): Chatting {
        val chatEntity = ChatJpaEntity()
        val participantEntities =
            chat.participants
                .map { participant ->
                    ChatParticipantJpaEntity(
                        chat = chatEntity,
                        user = UserJpaEntity.fromDomain(participant.user),
                        unreadCount = participant.unreadCount,
                    )
                }.toMutableList()

        chatEntity.participants.addAll(participantEntities)

        val savedChatEntity = chatJpaRepository.save(chatEntity)
        return savedChatEntity.toDomain()
    }

    override fun update(
        sender: User,
        chat: Chatting,
    ): Chatting {
        val participantEntities =
            chat.participants.map { participant ->
                ChatParticipantJpaEntity(
                    id = participant.id,
                    chat = ChatJpaEntity(id = chat.id), // Create a lightweight ChatJpaEntity reference
                    user = UserJpaEntity.fromDomain(participant.user),
                    unreadCount = participant.unreadCount,
                )
            }

        val messageEntities =
            chat.messages
                .map { message ->
                    MessageJpaEntity(
                        id = message.id,
                        sender = UserJpaEntity.fromDomain(message.sender),
                        chat = ChatJpaEntity(id = chat.id), // Use the chat ID to reference the chat
                        timeScript = message.timeScript,
                        content = message.content,
                    )
                }.toMutableList()

        val chatEntity =
            ChatJpaEntity(
                id = chat.id,
                participants = participantEntities.toMutableList(),
                messages = messageEntities,
            )

        val savedChatEntity = chatJpaRepository.save(chatEntity)
        return savedChatEntity.toDomain()
    }

    override fun findById(chatId: Long): Chatting? = chatJpaRepository.findById(chatId).orElse(null)?.toDomain()

    override fun findAllByUserId(userId: Long): List<Chatting> = chatJpaRepository.findAllByParticipantsUserId(userId).map { it.toDomain() }
}
