package com.org.framelt.user.adapter.out

import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity(name = "oauth_users")
@Table(
    uniqueConstraints = [
        UniqueConstraint(
            name = "UniqueProviderAndProviderUserId",
            columnNames = ["provider", "providerUserId"],
        ),
    ],
)
class OAuthUserJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @OneToOne
    @JoinColumn(name = "user_id")
    val user: UserJpaEntity,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val provider: OAuthProvider,
    @Column(nullable = false)
    val providerUserId: String,
)
