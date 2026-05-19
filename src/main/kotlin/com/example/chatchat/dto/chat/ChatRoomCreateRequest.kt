package com.example.chatchat.dto.chat

data class ChatRoomCreateRequest(
    val title: String,
    val isGroupChat: Boolean,
    val isSecret: Boolean,
    val password: String? = null // isSecret이 true일 때만 사용
)