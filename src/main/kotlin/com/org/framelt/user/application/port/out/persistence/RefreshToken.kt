package com.org.framelt.user.application.port.out.persistence

class RefreshToken(
    val id: Long? = null,
    val userId: Long,
    val token: String,
    var isValid: Boolean = true,
) {
    fun invalidate() {
        this.isValid = false
    }
}
