package com.org.framelt.chat.adapter.out

import com.org.framelt.chat.application.service.ChatCommendPort
import com.org.framelt.chat.application.service.ChatReadPort
import com.org.framelt.chat.domain.Chatting
import com.org.framelt.user.adapter.out.UserJpaEntity
import com.org.framelt.user.adapter.out.UserJpaRepository
import com.org.framelt.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class ChatRepository(
    private val chatJpaRepository: ChatJpaRepository,
    private val userJpaRepository: UserJpaRepository,
) : ChatCommendPort, ChatReadPort {

    override fun save(chat: Chatting): Chatting {
        val userEntities = chat.participant.map { participant ->
            UserJpaEntity(
                id = participant.id,
                name = participant.name,
                nickname = participant.nickname,
                profileImageUrl = participant.profileImageUrl,
                bio = participant.bio,
                identity = participant.identity,
                career = participant.career,
                shootingConcepts = participant.shootingConcepts.map { it.name },
                notificationsEnabled = participant.notificationsEnabled,
                deviceToken = participant.deviseToken
            )
        }
        val chatEntity = ChatJpaEntity(userEntities)
        val savedChatEntity = chatJpaRepository.save(chatEntity)
        return savedChatEntity.toDomain()
    }


    override fun update(sender: User, chat: Chatting): Chatting {
        val userEntities = chat.participant.map { participant ->
            UserJpaEntity(
                id = participant.id,
                name = participant.name,
                nickname = participant.nickname,
                profileImageUrl = participant.profileImageUrl,
                bio = participant.bio,
                identity = participant.identity,
                career = participant.career,
                shootingConcepts = participant.shootingConcepts.map { it.name },
                notificationsEnabled = participant.notificationsEnabled,
                deviceToken = participant.deviseToken
            )
        }
        val senderEntity = UserJpaEntity(
            id = sender.id,
            name = sender.name,
            nickname = sender.nickname,
            profileImageUrl = sender.profileImageUrl,
            bio = sender.bio,
            identity = sender.identity,
            career = sender.career,
            shootingConcepts = sender.shootingConcepts.map { it.name },
            notificationsEnabled = sender.notificationsEnabled,
            deviceToken = sender.deviseToken
        )
        val chatEntity = ChatJpaEntity(userEntities)
        val messageEntities = chat.messages.map { message ->
            MessageJpaEntity(
                sender = senderEntity,
                chat = chatEntity,
                timeScript = message.timeScript,
                content = message.content
            )
        }.toMutableList()
        chatEntity.updateMessage(messageEntities)

        val savedChatEntity = chatJpaRepository.save(chatEntity)
        return savedChatEntity.toDomain()
    }

    override fun findById(chatId: Long): Chatting? {
        return chatJpaRepository.findById(chatId).orElse(null)?.toDomain()
    }
}
