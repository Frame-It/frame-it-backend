package com.org.framelt.project.common

import com.org.framelt.project.adapter.`in`.request.ProjectCreateRequest
import com.org.framelt.project.adapter.`in`.request.ProjectUpdateRequest
import com.org.framelt.project.adapter.`in`.response.ProjectDetailManagerResponse
import com.org.framelt.project.adapter.`in`.response.ProjectDetailResponse
import com.org.framelt.project.application.port.`in`.ProjectCreateCommand
import com.org.framelt.project.application.port.`in`.ProjectDetailModel
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

        fun toResponse(projectDetail: ProjectDetailModel): ProjectDetailResponse =
            ProjectDetailResponse(
                id = projectDetail.id,
                title = projectDetail.title,
                shootingAt = projectDetail.shootingAt,
                locationType = projectDetail.locationType.name,
                spot = projectDetail.spot.name,
                concepts = projectDetail.concepts.map { it.name },
                conceptPhotoUrls = projectDetail.conceptPhotoUrls,
                description = projectDetail.description,
                retouchingDescription = projectDetail.retouchingDescription,
                manager =
                    ProjectDetailManagerResponse(
                        id = projectDetail.managerId,
                        nickname = projectDetail.managerNickname,
                        profileImageUrl = projectDetail.managerProfileImageUrl,
                        description = projectDetail.managerDescription,
                    ),
            )
    }
}
