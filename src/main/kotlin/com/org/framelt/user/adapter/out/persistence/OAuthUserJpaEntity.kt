package com.org.framelt.user.adapter.out.persistence

import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import com.org.framelt.user.application.port.out.persistence.OAuthUserModel
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
    @JoinColumn(name = "user_id", nullable = true)
    val user: UserJpaEntity?,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val provider: OAuthProvider,
    @Column(nullable = false)
    val providerUserId: String,
    @Column(nullable = false)
    val email: String,
) {
    companion object {
        fun fromModel(oAuthUserModel: OAuthUserModel) =
            OAuthUserJpaEntity(
                id = oAuthUserModel.id,
                user = oAuthUserModel.user?.let { UserJpaEntity.fromDomain(it) },
                provider = oAuthUserModel.provider,
                providerUserId = oAuthUserModel.providerUserId,
                email = oAuthUserModel.email,
            )
    }
}

fun OAuthUserJpaEntity.toModel() =
    OAuthUserModel(
        id = id!!,
        user = user?.toDomain(),
        provider = provider,
        providerUserId = providerUserId,
        email = email,
    )
