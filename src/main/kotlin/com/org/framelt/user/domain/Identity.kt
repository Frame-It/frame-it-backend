package com.org.framelt.user.domain

enum class Identity {
    PHOTOGRAPHER,
    MODEL,
    ;

    companion object {
        fun of(identity: String): Identity = valueOf(identity.uppercase())
    }
}
