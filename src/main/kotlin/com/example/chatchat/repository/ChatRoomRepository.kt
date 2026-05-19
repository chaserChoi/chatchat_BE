package com.example.chatchat.repository

import com.example.chatchat.domain.chat.ChatRoom
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {
    fun findAllByOrderByIdDesc(): List<ChatRoom>
}