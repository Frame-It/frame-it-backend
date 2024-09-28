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
    private val chatParticipantRepository: ChatParticipantRepository,
) : ChatCommendPort, ChatReadPort {

    override fun save(chat: Chatting): Chatting {
        val userEntities = chat.participants.map { participant ->
            UserJpaEntity(
                id = participant.user.id,
                name = participant.user.name,
                nickname = participant.user.nickname,
                birthDate = participant.user.birthDate,
                isQuit = participant.user.isQuit,
                profileImageUrl = participant.user.profileImageUrl,
                bio = participant.user.bio,
                identity = participant.user.identity,
                career = participant.user.career,
                shootingConcepts = participant.user.shootingConcepts,
                notificationsEnabled = participant.user.notificationsEnabled,
                email = participant.user.email,
                deviseToken = participant.user.deviseToken
            )
        }
        val chatEntity = ChatJpaEntity(userEntities)
        val savedChatEntity = chatJpaRepository.save(chatEntity)
        return savedChatEntity.toDomain()
    }
    override fun update(sender: User, chat: Chatting): Chatting {
        val userEntities = chat.participants.map { participant ->
            UserJpaEntity(
                id = participant.user.id,
                name = participant.user.name,
                nickname = participant.user.nickname,
                birthDate = participant.user.birthDate,
                isQuit = participant.user.isQuit,
                profileImageUrl = participant.user.profileImageUrl,
                bio = participant.user.bio,
                identity = participant.user.identity,
                career = participant.user.career,
                shootingConcepts = participant.user.shootingConcepts,
                notificationsEnabled = participant.user.notificationsEnabled,
                email = participant.user.email,
                deviseToken = participant.user.deviseToken
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

        val participantEntities = chat.participants.map { participant ->
            ChatParticipantJpaEntity(
                chat = ChatJpaEntity(chat.id),  // chatEntity 참조 추가
                user = userEntities.find { it.id == participant.user.id }
                    ?: throw IllegalArgumentException("User not found: ${participant.user.id}"),
                unreadCount = participant.unreadCount
            )
        }.toMutableList()

        val chatEntity = ChatJpaEntity(chat.id, participantEntities)

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


    override fun updateReadStatus(senderId: Long, chatId: Long) {
        val chatParticipant =
            chatParticipantRepository.findByChatIdAndUserId(chatId, senderId)
                ?: throw IllegalArgumentException("해당 채팅이 없습니다.")
        chatParticipant.readMessage()
        chatParticipantRepository.save(chatParticipant)
    }

    override fun findById(chatId: Long): Chatting? {
        return chatJpaRepository.findById(chatId).orElse(null)?.toDomain()
    }

    override fun findByUserId(userId: Long): List<Chatting> {
        return chatJpaRepository.findByUserId(userId).map { it.toDomain() }
    }
}
