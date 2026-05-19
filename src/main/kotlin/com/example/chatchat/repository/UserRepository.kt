package com.example.chatchat.repository

import com.example.chatchat.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByLoginId(loginId: String): User?
}