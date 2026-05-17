package com.example.chatchat.domain.chat

import com.example.chatchat.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "chat_rooms")
class ChatRoom(
    @Column(name = "title", nullable = false, length = 100)
    var title: String,

    @Column(name = "is_group_chat")
    var isGroupChat: Boolean = false,

    @Column(name = "is_secret")
    var isSecret: Boolean = false,

    @Column(name = "password")
    var password: String? = null
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
}