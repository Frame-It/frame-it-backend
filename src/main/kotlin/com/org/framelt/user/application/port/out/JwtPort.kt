package com.org.framelt.user.application.port.out

interface JwtPort {
    fun createToken(payload: String): String

    fun getSubject(token: String): String
}
