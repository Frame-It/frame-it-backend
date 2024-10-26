package com.org.framelt.portfolio.adapter.out

import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collaborator_id")
    val collaborator: UserJpaEntity?,
    val createAt: LocalDateTime,
)
