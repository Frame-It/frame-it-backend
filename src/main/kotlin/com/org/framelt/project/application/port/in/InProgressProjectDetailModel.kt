package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.ProjectMember
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.Status
import com.org.framelt.project.domain.TimeOption
import java.time.LocalDateTime

data class InProgressProjectDetailModel(
    val title: String,
    val spot: Spot,
    val timeOption: TimeOption,
    val shootingAt: LocalDateTime,
    val status: Status,
    val isHost: Boolean,
    val projectMember: InProgressProjectMemberModel,
) {
    companion object {
        fun fromDomain(
            project: Project,
            projectMember: ProjectMember,
        ) = InProgressProjectDetailModel(
            title = project.title,
            spot = project.spot,
            timeOption = project.timeOption,
            shootingAt = project.shootingAt,
            status = project.status,
            isHost = projectMember.isHost,
            projectMember = InProgressProjectMemberModel.fromDomain(projectMember),
        )
    }
}
