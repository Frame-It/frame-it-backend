package com.org.framelt.project.application.port.out

import com.org.framelt.project.domain.ProjectMember

interface ProjectMemberCommandPort {
    fun save(projectMember: ProjectMember)
}
