package com.org.framelt.project.adapter.out

import com.org.framelt.project.domain.Concept
import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.TimeOption
import com.org.framelt.user.adapter.out.UserJpaEntity
import com.org.framelt.user.adapter.out.toDomain
import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.LocationType
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
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity(name = "project")
class ProjectJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "manager_id")
    val manager: UserJpaEntity,
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
) {
    companion object {
        fun fromDomain(project: Project) =
            ProjectJpaEntity(
                id = project.id,
                manager = UserJpaEntity.fromDomain(project.manager),
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
            )
    }
}

fun ProjectJpaEntity.toDomain() =
    Project(
        id = id,
        manager = manager.toDomain(),
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
    )
