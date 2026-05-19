package com.example.chatchat.repository

import com.example.chatchat.domain.chat.ChatRoomMember
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRoomMemberRepository : JpaRepository<ChatRoomMember, Long> {
}