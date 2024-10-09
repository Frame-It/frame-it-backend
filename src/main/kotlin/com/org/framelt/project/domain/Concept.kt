package com.org.framelt.project.domain

enum class Concept(
    val code: String,
) {
    MONOTONE("PC-001"),
    MYSTERIOUS("PC-002"),
    RETRO("PC-003"),
    SNAP("PC-004"),
    STREET("PC-005"),
    NATURE("PC-006"),
    BLOOM("PC-007"),
    VINTAGE("PC-008"),
    PROFILE("PC-009"),
    BODY_PROFILE("PC-010"),
    TRAVEL("PC-011"),
    KPOP("PC-012"),
    COSPLAY("PC-013"),
    ;

    companion object {
        fun of(concept: String): Concept = valueOf(concept.uppercase())

        fun fromCode(code: String): Concept =
            entries.find { it.code == code } ?: throw IllegalArgumentException("존재하지 않는 프로젝트 컨셉  코드입니다: $code")
    }
}
