package com.org.framelt.project.domain

enum class ProjectApplicantCancelReason(
    val code: String,
) {
    WRONG_INPUT("CR-001"),
    OPINION_MISMATCHED("CR-002"),
    SCHEDULE_CONFLICT("CR-003"),
    OTHER("CR-004"),
    ;

    companion object {
        fun fromCode(code: String): ProjectApplicantCancelReason =
            entries.find { it.code == code }
                ?: throw IllegalArgumentException("해당하는 취소 사유가 존재하지 않습니다.: $code")
    }
}
