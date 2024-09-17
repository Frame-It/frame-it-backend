package com.org.framelt.chat.application.service

import CreateChatCommand
import SendMessageCommand
import com.org.framelt.chat.adapter.`in`.ChatMapper
import com.org.framelt.chat.adapter.`in`.ChattingResponse
import com.org.framelt.chat.application.port.`in`.ChatUseCase
import com.org.framelt.chat.domain.Chatting
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

        val newChat = Chatting(listOf(sender, receiver))
        val saveChat = chatCommendPort.save(newChat) ?: throw IllegalStateException("Save chat fail")
        return saveChat.id;
    }

    override fun sendMessage(command: SendMessageCommand) {
        val chat = chatReadPort.getById(command.chatId)
        val sender = userQueryPort.readById(command.userId)
        chat.addMessage(sender, command.content)
        chatCommendPort.update(sender, chat)
    }

    override fun getChat(senderId: Long, chatId: Long): ChattingResponse {
        val sender = userQueryPort.readById(senderId)
        val chat = chatReadPort.findById(chatId) ?: throw IllegalArgumentException("채팅방을 찾을 수 없습니다: $chatId")
        return ChatMapper.toResponse(sender, chat)
    }
}
