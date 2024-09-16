package com.org.framelt.user.adapter.out.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne

@Entity(name = "quit_user")
class QuitUserJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @OneToOne
    @JoinColumn(name = "user_id")
    val user: UserJpaEntity,
    @Column(nullable = true)
    val quitReason: String?,
) {
    companion object {
        fun fromDomain(
            user: UserJpaEntity,
            quitReason: String?,
        ): QuitUserJpaEntity =
            QuitUserJpaEntity(
                user = user,
                quitReason = quitReason,
            )
    }
}
