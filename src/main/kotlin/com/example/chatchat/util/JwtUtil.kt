package com.example.chatchat.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${spring.jwt.secret}")
    private val secret: String,

    @Value("\${spring.jwt.expiration}")
    private val expiration: Long
) {

    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun generateToken(loginId: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expiration)

        return Jwts.builder()
            .subject(loginId)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey)
            .compact()
    }

    fun getLoginIdFromToken(token: String): String {
        return getClaimsFromToken(token).subject
    }

    fun validateToken(token: String): Boolean {
        try {
            getClaimsFromToken(token)
            return true
        } catch (e: Exception) {
            // MalformedJwtException, ExpiredJwtException, etc.
            return false
        }
    }

    private fun getClaimsFromToken(token: String): Claims {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
    }
}