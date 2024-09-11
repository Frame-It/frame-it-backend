package com.org.framelt.user.domain

enum class Identity {
    PHOTOGRAPHER,
    MODEL,
    NONE,
    ;

    companion object {
        fun of(identity: String): Identity = valueOf(identity.uppercase())
    }
}
