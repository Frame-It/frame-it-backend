package com.org.framelt.project.application.port.out

import com.org.framelt.project.domain.ProjectMember
import com.org.framelt.project.domain.Status

interface ProjectMemberQueryPort {
    fun readAllByProjectId(projectId: Long): List<ProjectMember>

    fun readByMemberIdAndProjectId(
        memberId: Long,
        projectId: Long,
    ): ProjectMember

    fun existsByMemberIdAndProjectStatus(
        memberId: Long,
        status: Status,
    ): Boolean

    fun readAllByUserId(userId: Long): List<ProjectMember>
}
