package com.org.framelt.project.adapter.out

import com.org.framelt.project.application.port.out.ProjectApplicantCommandPort
import com.org.framelt.project.domain.ProjectApplicant
import org.springframework.stereotype.Repository

@Repository
class ProjectApplicantRepository(
    private val projectApplicantJpaRepository: ProjectApplicantJpaRepository,
) : ProjectApplicantCommandPort {
    override fun save(projectApplicant: ProjectApplicant) {
        val projectApplicantJpaEntity = ProjectApplicantJpaEntity.fromDomain(projectApplicant)
        projectApplicantJpaRepository.save(projectApplicantJpaEntity)
    }
}
