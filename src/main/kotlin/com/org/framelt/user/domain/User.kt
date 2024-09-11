package com.org.framelt.user.domain

class User(
    val id: Long? = null,
    val name: String,
    val nickname: String,
    val profileImageUrl: String? = null,
    val bio: String? = null,
    val identity: Identity,
    val career: String? = null,
    val shootingConcepts: List<Concept>,
    var description: String? = null,
    val notificationsEnabled: Boolean, // 보유
    val deviseToken: String? = null,
) {
    companion object {
        fun beforeCompleteSignUp() =
            User(
                name = IN_SIGN_UP_PROGRESS,
                nickname = IN_SIGN_UP_PROGRESS,
                identity = Identity.NONE,
                shootingConcepts = emptyList(),
                notificationsEnabled = false,
            )

        private const val IN_SIGN_UP_PROGRESS = "회원가입 진행 중"
    }
}
