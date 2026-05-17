package com.example.chatchat.domain.chat

import com.example.chatchat.domain.user.User
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "messages")
@EntityListeners(AuditingEntityListener::class)
class Message(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    var chatRoom: ChatRoom,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    var sender: User,

    @Column(name = "content", columnDefinition = "TEXT")
    var content: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false, length = 20)
    var messageType: MessageType,

    @Column(name = "file_url")
    var fileUrl: String? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now()
        protected set
}