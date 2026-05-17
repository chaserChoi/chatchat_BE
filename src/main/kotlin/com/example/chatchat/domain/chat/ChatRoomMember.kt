package com.example.chatchat.domain.chat

import com.example.chatchat.domain.user.User
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(
    name = "chat_room_members",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_room_user", columnNames = ["room_id", "user_id"])
    ]
)
@EntityListeners(AuditingEntityListener::class)
class ChatRoomMember(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    var chatRoom: ChatRoom,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @Column(name = "last_read_message_id")
    var lastReadMessageId: Long? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    @CreatedDate
    @Column(name = "joined_at", nullable = false, updatable = false)
    var joinedAt: LocalDateTime = LocalDateTime.now()
        protected set
}