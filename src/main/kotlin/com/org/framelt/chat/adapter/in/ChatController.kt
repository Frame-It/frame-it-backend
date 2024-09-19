package com.org.framelt.chat.adapter.`in`

import com.org.framelt.chat.application.port.`in`.ChatUseCase
import com.org.framelt.config.auth.Authorization
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chats")
class ChatController(
    private val chatUseCase: ChatUseCase,
) {
    @PostMapping
    fun createChat(
        @Authorization userId: Long,
        @RequestBody request: CreateChatRequest,
    ): ResponseEntity<Long> {
        val command = ChatMapper.toCreateCommand(userId, request)
        val chatId = chatUseCase.createChat(command)
        return ResponseEntity.ok(chatId)
    }

    @PostMapping("/{chatId}/messages")
    fun sendMessage(
        @Authorization userId: Long,
        @RequestBody request: SendMessageRequest,
    ): ResponseEntity<Void> {
        val command = ChatMapper.toSendMessageCommand(userId, request)
        chatUseCase.sendMessage(command)
        return ResponseEntity.ok().build()
    }

    // 채팅방 조회
    @GetMapping("/{chatId}")
    fun getChat(@Authorization userId: Long, @PathVariable chatId: Long): ResponseEntity<ChattingResponse> {
        val chat = chatUseCase.getChat(userId, chatId)
        return ResponseEntity.ok(chat)
    }
}
