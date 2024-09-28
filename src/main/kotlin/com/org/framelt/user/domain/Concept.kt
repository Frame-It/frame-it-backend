package com.org.framelt.user.domain

enum class Concept {
    SNAP,
    PORTRAIT,
    ;

    // 등등

    companion object {
        fun from(value: String): Concept = Concept.valueOf(value.uppercase())
    }
}
