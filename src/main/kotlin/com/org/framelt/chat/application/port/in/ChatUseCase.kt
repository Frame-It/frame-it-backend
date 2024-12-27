package com.org.framelt.chat.application.port.`in`

import com.org.framelt.chat.adapter.`in`.ChatRoomInfoResponse
import com.org.framelt.chat.adapter.`in`.ChattingResponse
import com.org.framelt.chat.application.port.out.CreateChatCommand
import com.org.framelt.chat.application.port.out.SendMessageCommand

interface ChatUseCase {
    fun createChat(command: CreateChatCommand): Long

    fun sendMessage(command: SendMessageCommand)

    fun getChat(
        userId: Long,
        chatId: Long,
    ): ChattingResponse

    fun getChattingRoom(userId: Long): List<ChatRoomInfoResponse>

    fun getChatRoomId(
        userId: Long,
        participantId: Long,
    ): Long?
}
