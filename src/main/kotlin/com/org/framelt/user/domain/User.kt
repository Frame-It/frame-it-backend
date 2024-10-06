package com.org.framelt.user.domain

import java.time.LocalDate
import java.util.UUID

class User(
    val id: Long? = null,
    var name: String,
    var nickname: String,
    val profileImageUrl: String? = null,
    val bio: String? = null,
    var identity: Identity,
    val career: String? = null,
    val shootingConcepts: List<Concept>,
    var description: String? = null,
    var birthDate: LocalDate? = null,
    var notificationsEnabled: Boolean, // 보유
    var email: String,
    val deviseToken: String? = null,
    var isQuit: Boolean = false,
) {
    fun fillProfile(
        name: String,
        nickname: String,
        identity: Identity,
        birthDate: LocalDate,
        notificationsEnabled: Boolean,
    ) {
        this.name = name
        this.nickname = nickname
        this.identity = identity
        this.birthDate = birthDate
        this.notificationsEnabled = notificationsEnabled
    }

    fun quit() {
        isQuit = true
        email = "quit_" + UUID.randomUUID().toString()
    }

    fun updateProfile(
        profileImageUrl: String?,
        nickname: String,
        description: String,
        concepts: List<Concept>,
    ): User =
        User(
            id = this.id,
            name = this.name,
            nickname = nickname,
            profileImageUrl = profileImageUrl,
            bio = this.bio,
            identity = this.identity,
            career = this.career,
            shootingConcepts = concepts,
            description = description,
            birthDate = this.birthDate,
            notificationsEnabled = this.notificationsEnabled,
            email = this.email,
            deviseToken = this.deviseToken,
            isQuit = this.isQuit,
        )

    fun isSignUpCompleted(): Boolean = identity != Identity.NONE

    companion object {
        fun beforeCompleteSignUp(email: String) =
            User(
                name = IN_SIGN_UP_PROGRESS,
                nickname = IN_SIGN_UP_PROGRESS + UUID.randomUUID().toString(),
                identity = Identity.NONE,
                shootingConcepts = emptyList(),
                notificationsEnabled = false,
                email = email,
            )

        private const val IN_SIGN_UP_PROGRESS = "Unknown"
    }
}
