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
) : ChatCommendPort, ChatReadPort {

    override fun save(chat: Chatting): Chatting {
        val userEntities = chat.participant.map { participant ->
            UserJpaEntity(
                id = participant.id,
                name = participant.name,
                nickname = participant.nickname,
                birthDate = participant.birthDate,
                isQuit = participant.isQuit,
                profileImageUrl = participant.profileImageUrl,
                bio = participant.bio,
                identity = participant.identity,
                career = participant.career,
                shootingConcepts = participant.shootingConcepts,
                notificationsEnabled = participant.notificationsEnabled,
                email = participant.email,
                deviseToken = participant.deviseToken
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
                birthDate = participant.birthDate,
                isQuit = participant.isQuit,
                profileImageUrl = participant.profileImageUrl,
                bio = participant.bio,
                identity = participant.identity,
                career = participant.career,
                shootingConcepts = participant.shootingConcepts,
                notificationsEnabled = participant.notificationsEnabled,
                email = participant.email,
                deviseToken = participant.deviseToken
            )
        }
        val senderEntity = UserJpaEntity(
            id = sender.id,
            name = sender.name,
            nickname = sender.nickname,
            birthDate = sender.birthDate,
            isQuit = sender.isQuit,
            profileImageUrl = sender.profileImageUrl,
            bio = sender.bio,
            identity = sender.identity,
            career = sender.career,
            shootingConcepts = sender.shootingConcepts,
            notificationsEnabled = sender.notificationsEnabled,
            email = sender.email,
            deviseToken = sender.deviseToken
        )
        val chatEntity = ChatJpaEntity(chat.id, userEntities)
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
