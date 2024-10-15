package com.org.framelt.chat.application.port.`in`

import CreateChatCommand
import SendMessageCommand
import com.org.framelt.chat.adapter.`in`.ChatRoomInfoResponse
import com.org.framelt.chat.adapter.`in`.ChattingResponse

interface ChatUseCase {
    fun createChat(command: CreateChatCommand): Long
    fun sendMessage(command: SendMessageCommand)
    fun getChat(userId: Long, chatId: Long): ChattingResponse
    fun getChattingRoom(userId: Long): List<ChatRoomInfoResponse>
    fun getChatRoomId(userId: Long, participantId: Long): Long?
}
