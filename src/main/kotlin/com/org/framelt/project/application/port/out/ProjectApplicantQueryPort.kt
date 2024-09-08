package com.org.framelt.project.application.port.out

import com.org.framelt.project.domain.ProjectApplicant

interface ProjectApplicantQueryPort {
    fun readByProjectId(projectId: Long): List<ProjectApplicant>

    fun readByProjectIdAndApplicantId(
        projectId: Long,
        applicantId: Long,
    ): ProjectApplicant

    fun existsByProjectIdAndApplicantId(
        projectId: Long,
        applicantId: Long,
    ): Boolean
}
