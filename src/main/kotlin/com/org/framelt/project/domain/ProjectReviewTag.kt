package com.org.framelt.project.domain

enum class ProjectReviewTag(
    val code: String,
) {
    POSE_GOOD("RE-001"),
    RETOUCH_GOOD("RE-002"),
    KIND("RE-003"),
    ;

    companion object {
        fun fromCode(code: String): ProjectReviewTag =
            entries.firstOrNull { it.code == code }
                ?: throw IllegalArgumentException("프로젝트 리뷰 태그에 매칭되지 않는 코드입니다.: $code")
    }
}
