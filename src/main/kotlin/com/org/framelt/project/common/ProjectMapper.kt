package com.org.framelt.project.common

import com.org.framelt.project.adapter.`in`.request.ProjectCreateRequest
import com.org.framelt.project.adapter.`in`.request.ProjectUpdateRequest
import com.org.framelt.project.application.port.`in`.ProjectCreateCommand
import com.org.framelt.project.application.port.`in`.ProjectUpdateCommand
import com.org.framelt.project.domain.Concept
import com.org.framelt.user.domain.Identity

class ProjectMapper {
    companion object {
        fun toCommand(request: ProjectCreateRequest): ProjectCreateCommand =
            ProjectCreateCommand(
                userId = request.userId,
                title = request.title,
                recruitmentRole = Identity.of(request.recruitmentRole),
                shootingAt = request.shootingAt,
                locationType = request.locationType,
                spot = request.spot,
                concepts = request.concepts.map { Concept.of(it) },
                conceptPhotoUrls = request.conceptPhotoUrls,
                description = request.description,
                retouchingDescription = request.retouchingDescription,
            )

        fun toCommand(request: ProjectUpdateRequest): ProjectUpdateCommand =
            ProjectUpdateCommand(
                projectId = request.projectId,
                title = request.title,
                shootingAt = request.shootingAt,
                locationType = request.locationType,
                spot = request.spot,
                concepts = request.concepts.map { Concept.of(it) },
                conceptPhotoUrls = request.conceptPhotoUrls,
                description = request.description,
                retouchingDescription = request.retouchingDescription,
            )
    }
}
