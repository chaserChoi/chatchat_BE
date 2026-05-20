package com.example.chatchat.repository

import com.example.chatchat.domain.chat.Message
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface MessageRepository : JpaRepository<Message, Long> {
    // 특정 채팅방의 메시지를 ID(생성순) 역순으로 페이징하여 조회
    fun findByChatRoomIdOrderByIdDesc(roomId: Long, pageable: Pageable): Page<Message>
}