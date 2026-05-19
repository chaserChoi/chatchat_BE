package com.example.chatchat.dto.chat

import com.example.chatchat.domain.chat.ChatRoom
import java.time.LocalDateTime

data class ChatRoomResponse(
    val id: Long,
    val title: String,
    val isGroupChat: Boolean,
    val isSecret: Boolean,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(chatRoom: ChatRoom): ChatRoomResponse {
            return ChatRoomResponse(
                id = chatRoom.id,
                title = chatRoom.title,
                isGroupChat = chatRoom.isGroupChat,
                isSecret = chatRoom.isSecret,
                createdAt = chatRoom.createdAt
            )
        }
    }
}