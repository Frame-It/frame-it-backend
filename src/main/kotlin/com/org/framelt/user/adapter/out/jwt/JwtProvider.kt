package com.org.framelt.user.adapter.out.jwt

import com.org.framelt.user.application.port.out.JwtPort
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

@Component
class JwtProvider(
    @Value("\${jwt.secret-key}") private val secretKey: String,
    @Value("\${jwt.access-token-expire-time-in-seconds}") private val accessTokenExpireTimeInSeconds: Long,
) : JwtPort {
    private val key: Key by lazy { Keys.hmacShaKeyFor(secretKey.toByteArray()) }

    override fun createToken(payload: String): String {
        val claims = Jwts.claims().setSubject(payload)
        val now = Instant.now()
        val expiration = now.plus(accessTokenExpireTimeInSeconds, ChronoUnit.SECONDS)
        return Jwts
            .builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(expiration))
            .signWith(key)
            .compact()
    }

    override fun getSubject(token: String): String =
        getClaimsJws(token)
            .body
            .subject

    fun isValidToken(token: String): Boolean =
        try {
            getClaimsJws(token)
            true
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }

    private fun getClaimsJws(token: String) =
        Jwts
            .parserBuilder()
            .setSigningKey(key.encoded)
            .build()
            .parseClaimsJws(token)
}
