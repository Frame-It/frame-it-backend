package com.org.framelt.project.adapter.out

import com.org.framelt.project.domain.*
import com.org.framelt.user.adapter.out.persistence.UserJpaEntity
import com.org.framelt.user.adapter.out.persistence.toDomain
import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.LocationType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "project")
class ProjectJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "host_id")
    val host: UserJpaEntity,
    @Column(nullable = false)
    val title: String,
    @Enumerated(EnumType.STRING)
    val recruitmentRole: Identity,
    @Column(nullable = false)
    val shootingAt: LocalDateTime,
    @Enumerated(EnumType.STRING)
    val timeOption: TimeOption,
    @Enumerated(EnumType.STRING)
    val locationType: LocationType,
    @Enumerated(EnumType.STRING)
    val spot: Spot,
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Concept::class)
    @CollectionTable(
        name = "project_concepts",
        joinColumns = [JoinColumn(name = "project_id")],
    )
    val concepts: List<Concept>,
    @ElementCollection
    @CollectionTable(
        name = "project_concept_photo_urls",
        joinColumns = [JoinColumn(name = "project_id")],
    )
    val conceptPhotoUrls: List<String>,
    @Column(nullable = false)
    val description: String,
    @Column(nullable = true)
    val retouchingDescription: String?,
    @Enumerated(EnumType.STRING)
    val status: Status,
    @Column(nullable = false)
    val viewCount: Int = 0,
) {
    companion object {
        fun fromDomain(project: Project) =
            ProjectJpaEntity(
                id = project.id,
                host = UserJpaEntity.fromDomain(project.host),
                title = project.title,
                recruitmentRole = project.recruitmentRole,
                shootingAt = project.shootingAt,
                timeOption = project.timeOption,
                locationType = project.locationType,
                spot = project.spot,
                concepts = project.concepts,
                conceptPhotoUrls = project.conceptPhotoUrls,
                description = project.description,
                retouchingDescription = project.retouchingDescription,
                status = project.status,
                viewCount = project.viewCount,
            )
    }
}

fun ProjectJpaEntity.toDomain() =
    Project(
        id = id,
        host = host.toDomain(),
        title = title,
        recruitmentRole = recruitmentRole,
        shootingAt = shootingAt,
        timeOption = timeOption,
        locationType = locationType,
        spot = spot,
        concepts = concepts,
        conceptPhotoUrls = conceptPhotoUrls,
        description = description,
        retouchingDescription = retouchingDescription,
        status = status,
        viewCount = viewCount,
    )
