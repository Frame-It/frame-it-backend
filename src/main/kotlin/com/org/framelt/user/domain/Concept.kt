package com.org.framelt.user.domain

enum class Concept(
    val code: String,
) {
    MONOTONE("UC-001"),
    MYSTERIOUS("UC-002"),
    RETRO("UC-003"),
    SNAP("UC-004"),
    STREET("UC-005"),
    NATURE("UC-006"),
    BLOOM("UC-007"),
    VINTAGE("UC-008"),
    PROFILE("UC-009"),
    BODY_PROFILE("UC-010"),
    TRAVEL("UC-011"),
    KPOP("UC-012"),
    COSPLAY("UC-013"),
    ;

    companion object {
        fun fromCode(code: String): Concept =
            entries.find { it.code == code } ?: throw IllegalArgumentException("존재하지 않는 사용자 컨셉 코드입니다: $code")
    }
}
