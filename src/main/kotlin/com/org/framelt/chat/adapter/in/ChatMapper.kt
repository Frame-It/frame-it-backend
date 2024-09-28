package com.org.framelt.chat.adapter.`in`

import CreateChatCommand
import SendMessageCommand
import com.org.framelt.chat.domain.Chatting
import com.org.framelt.user.domain.User

class ChatMapper {
    companion object {
        fun toCreateCommand(userId: Long, request: CreateChatRequest): CreateChatCommand {
            return CreateChatCommand(userId, request.participantId)
        }

        fun toSendMessageCommand(userId: Long, command: SendMessageRequest): SendMessageCommand {
            return SendMessageCommand(userId, command.chatId, command.content)
        }

        fun toResponse(sender: User, chat: Chatting): ChattingResponse {
            val participantResponses = chat.participants.map { user ->
                ChatUserInfoResponse(
                    id = user.user.id!!,
                    name = user.user.name,
                    profileImageUrl = sender.profileImageUrl.toString()
                )
            }


            val messageResponses = chat.messages.map { message ->
                MessageResponse(
                    messageId = message.id,
                    sender = ChatUserInfoResponse(
                        id = sender.id!!,
                        name = sender.name,
                        profileImageUrl = sender.profileImageUrl.toString()
                    ),
                    timeStamp = message.timeScript,
                    content = message.content,
                    isMe = message.sender.id == sender.id
                )
            }.sortedWith(compareBy<MessageResponse> { !it.isMe }.thenBy { it.timeStamp })

            return ChattingResponse(
                chatId = chat.id,
                participants = participantResponses,
                messages = messageResponses
            )
        }

    }
}
