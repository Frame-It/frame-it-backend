package com.org.framelt.user.application.port.out

import io.jsonwebtoken.Claims

interface JwtPort {
    fun createAccessToken(payload: String): String

    fun parseToken(token: String): Claims

    fun createRefreshToken(payload: String): String
}
