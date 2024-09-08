package com.org.framelt.project.application.port.out

import com.org.framelt.project.domain.ProjectMember

interface ProjectMemberQueryPort {
    fun readAllByProjectId(projectId: Long): List<ProjectMember>
}
