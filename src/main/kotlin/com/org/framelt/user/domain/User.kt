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
    val shootingConcepts: List<UserConcept>,
    var description: String? = null,
    var birthDate: LocalDate? = null,
    var notificationsEnabled: Boolean, // 보유
    var email: String,
    var deviseToken: String? = null,
    var isQuit: Boolean = false,
) {
    fun quit() {
        isQuit = true
        nickname = "quit_" + UUID.randomUUID().toString()
        email = "quit_" + UUID.randomUUID().toString()
    }

    fun updateProfile(
        profileImageUrl: String?,
        description: String?,
        concepts: List<UserConcept>?,
    ): User =
        User(
            id = this.id,
            name = this.name,
            nickname = nickname,
            profileImageUrl = profileImageUrl,
            bio = this.bio,
            identity = this.identity,
            career = this.career,
            shootingConcepts = concepts ?: emptyList(),
            description = description,
            birthDate = this.birthDate,
            notificationsEnabled = this.notificationsEnabled,
            email = this.email,
            deviseToken = this.deviseToken,
            isQuit = this.isQuit,
        )

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

    fun updateDeviseToken(deviseToken: String?) {
        this.deviseToken = deviseToken
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        return id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}
