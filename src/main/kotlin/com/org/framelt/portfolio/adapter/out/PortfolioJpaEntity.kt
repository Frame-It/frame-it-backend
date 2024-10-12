package com.org.framelt.portfolio.adapter.out

import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "portfolios")
class PortfolioJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val manage: UserJpaEntity,

    @Column(nullable = false)
    val title: String,

    @Column
    val description: String? = null,

    @Column(nullable = false)
    val primaryPhoto: String,

    @ElementCollection
    @CollectionTable(name = "portfolio_photos", joinColumns = [JoinColumn(name = "portfolio_id")])
    @Column(name = "photo")
    val photos: List<String>,

    @ElementCollection
    @CollectionTable(name = "portfolio_hashtags", joinColumns = [JoinColumn(name = "portfolio_id")])
    @Column(name = "hashtag")
    val hashtags: List<String>? = null,

    @ManyToMany
    @JoinTable(
        name = "portfolio_collaborators",
        joinColumns = [JoinColumn(name = "portfolio_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val collaborators: List<UserJpaEntity>,
    val createAt: LocalDateTime,
)
