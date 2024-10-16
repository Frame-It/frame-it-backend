package com.org.framelt.chat.application.service

import CreateChatCommand
import SendMessageCommand
import com.org.framelt.chat.adapter.`in`.ChatMapper
import com.org.framelt.chat.adapter.`in`.ChatRoomInfoResponse
import com.org.framelt.chat.adapter.`in`.ChatRoomUserInfoResponse
import com.org.framelt.chat.adapter.`in`.ChattingResponse
import com.org.framelt.chat.application.port.`in`.ChatUseCase
import com.org.framelt.chat.domain.Chatting
import com.org.framelt.chat.domain.Participant
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatCommendPort: ChatCommendPort,
    private val chatReadPort: ChatReadPort,
    private val userQueryPort: UserQueryPort,
) : ChatUseCase {
    override fun createChat(command: CreateChatCommand): Long {
        val sender = userQueryPort.readById(command.userId)
        val receiver = userQueryPort.readById(command.participantId)
        val existingChat = chatCommendPort.findChatBetweenUsers(sender.id!!, receiver.id!!)
        existingChat?.let {
            throw IllegalArgumentException("이미 생성된 채팅방 입니다. 채팅방ID: ${it.id}")
        }
        val newChat = Chatting(listOf(Participant(sender), Participant(receiver)))

        val saveChat = chatCommendPort.save(newChat) ?: throw IllegalStateException("Save chat fail")
        return saveChat.id
    }

    override fun sendMessage(command: SendMessageCommand) {
        val chat = chatReadPort.getById(command.chatId)
        val sender = userQueryPort.readById(command.userId)
        userQueryPort.readById(command.receiverId)
        chat.addMessage(sender, command.content)
        chat.increaseUnreadCount(command.receiverId)
        chatCommendPort.update(sender, chat)
    }

    override fun getChat(
        userId: Long,
        chatId: Long,
    ): ChattingResponse {
        val user = userQueryPort.readById(userId)
        val chat = chatReadPort.findById(chatId) ?: throw IllegalArgumentException("채팅방을 찾을 수 없습니다: $chatId")
        chat.updateUnreadCount(userId)
        chatCommendPort.update(user, chat)
        return ChatMapper.toResponse(user, chat)
    }

    override fun getChattingRoom(userId: Long): List<ChatRoomInfoResponse> {
        val user = userQueryPort.readById(userId)

        return chatReadPort.findAllByUserId(userId).map { chatRoom ->
            val lastMessage = chatRoom.messages.lastOrNull()?.content ?: "No messages yet"
            val lastMessageTime = chatRoom.messages.lastOrNull()?.timeScript // Correctly get the timestamp of the last message
            val unreadMessageCount = chatRoom.participants.find { it.user.id == userId }?.unreadCount ?: 0

            val participantInfo =
                chatRoom.participants
                    .filter { it.user.id != userId }
                    .map { participant ->
                        ChatRoomUserInfoResponse(
                            id = participant.user.id!!,
                            name = participant.user.name,
                            profileImageUrl = participant.user.profileImageUrl ?: "",
                            identity = participant.user.identity.name,
                        )
                    }.first()

            ChatRoomInfoResponse(
                chatId = chatRoom.id,
                participants = participantInfo,
                lastMessage = lastMessage,
                lastMessageTime = lastMessageTime?.toString() ?: "No timestamp available", // Convert LocalDateTime to string for display
                unreadMessageCount = unreadMessageCount,
            )
        }
    }

    override fun getChatRoomId(
        userId: Long,
        participantId: Long,
    ): Long? = chatCommendPort.findChatBetweenUsers(userId, participantId)?.id
}
