package com.org.framelt.user.adapter.out

import com.org.framelt.user.domain.Concept
import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.User
import jakarta.persistence.*
@Entity
@Table(name = "users")
class UserJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val nickname: String,

    @Column(name = "profile_image_url")
    val profileImageUrl: String? = null,

    val bio: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val identity: Identity,

    val career: String? = null,

    @ElementCollection
    @CollectionTable(name = "user_concepts", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "concept")
    val shootingConcepts: List<String>,

    @Column(name = "notifications_enabled", nullable = false)
    val notificationsEnabled: Boolean,

    @Column(name = "device_token")
    val deviceToken: String? = null
) {
    fun toDomain(): User {
        return User(
            id = this.id,
            name = this.name,
            nickname = this.nickname,
            profileImageUrl = this.profileImageUrl,
            bio = this.bio,
            identity = this.identity,
            career = this.career,
            shootingConcepts = this.shootingConcepts.map { Concept.SNAP },
            notificationsEnabled = this.notificationsEnabled,
            deviseToken = this.deviceToken
        )
    }
}
