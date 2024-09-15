package com.org.framelt.user.adapter.out.persistence

import com.org.framelt.user.domain.Concept
import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.User
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import java.time.LocalDate

@Entity(name = "users")
class UserJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val nickname: String,
    @Column(nullable = true)
    val profileImageUrl: String? = null,
    @Column(nullable = true)
    val bio: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val identity: Identity,
    @Column(nullable = true)
    val career: String? = null,
    @ElementCollection(targetClass = Concept::class)
    @CollectionTable(
        name = "user_shooting_concepts",
        joinColumns = [JoinColumn(name = "user_id")],
    )
    @Enumerated(EnumType.STRING)
    val shootingConcepts: List<Concept>,
    @Column(nullable = true)
    val description: String? = null,
    @Column(nullable = true)
    val birthDate: LocalDate?,
    @Column(nullable = false)
    val notificationsEnabled: Boolean, // 보유
    @Column(nullable = true)
    val deviseToken: String? = null,
    @Column(nullable = false, unique = true)
    val email: String,
) {
    companion object {
        fun fromDomain(user: User) =
            UserJpaEntity(
                id = user.id,
                name = user.name,
                nickname = user.nickname,
                profileImageUrl = user.profileImageUrl,
                bio = user.bio,
                identity = user.identity,
                career = user.career,
                shootingConcepts = user.shootingConcepts,
                description = user.description,
                birthDate = user.birthDate,
                notificationsEnabled = user.notificationsEnabled,
                deviseToken = user.deviseToken,
                email = user.email,
            )
    }
}

fun UserJpaEntity.toDomain() =
    User(
        id = this.id,
        name = this.name,
        nickname = this.nickname,
        profileImageUrl = this.profileImageUrl,
        bio = this.bio,
        identity = this.identity,
        career = this.career,
        shootingConcepts = this.shootingConcepts,
        description = this.description,
        notificationsEnabled = this.notificationsEnabled,
        deviseToken = this.deviseToken,
        birthDate = this.birthDate,
        email = this.email,
    )
