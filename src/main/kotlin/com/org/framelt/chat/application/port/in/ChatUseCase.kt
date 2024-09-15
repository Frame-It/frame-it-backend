package com.org.framelt.chat.application.port.`in`

import CreateChatCommand
import SendMessageCommand
import com.org.framelt.chat.adapter.`in`.ChattingResponse

interface ChatUseCase {
    fun createChat(command: CreateChatCommand): Long
    fun sendMessage(command: SendMessageCommand)
    fun getChat(senderId: Long, chatId: Long): ChattingResponse
}
