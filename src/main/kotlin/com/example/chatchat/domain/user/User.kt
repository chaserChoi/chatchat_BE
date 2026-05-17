package com.example.chatchat.domain.user

import com.example.chatchat.domain.common.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(name = "login_id", nullable = false, unique = true, length = 50)
    var loginId: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "nickname", nullable = false, length = 50)
    var nickname: String,

    @Column(name = "profile_image_url")
    var profileImageUrl: String? = null
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
}