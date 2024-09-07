package com.org.framelt.project.application.port.out

import com.org.framelt.project.domain.ProjectApplicant

interface ProjectApplicantCommandPort {
    fun save(projectApplicant: ProjectApplicant)
}
