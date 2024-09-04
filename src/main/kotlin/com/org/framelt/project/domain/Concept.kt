package com.org.framelt.project.domain

enum class Concept {
    MONOTONE,
    VINTAGE,
    PROFILE,
    BODY_PROFILE, ;

    companion object {
        fun of(concept: String): Concept = valueOf(concept.uppercase())
    }
}
