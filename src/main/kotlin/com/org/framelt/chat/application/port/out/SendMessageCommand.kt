package com.org.framelt.chat.application.port.out

data class SendMessageCommand(val userId: Long, val chatId: Long, val receiverId: Long, val content: String)
