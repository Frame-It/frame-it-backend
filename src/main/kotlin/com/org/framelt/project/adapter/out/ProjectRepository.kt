package com.org.framelt.project.adapter.out

import com.org.framelt.project.application.port.out.ProjectCommandPort
import com.org.framelt.project.domain.Project
import org.springframework.stereotype.Repository

@Repository
class ProjectRepository(
    private val projectJpaRepository: ProjectJpaRepository,
    private val projectApplicantJpaRepository: ProjectApplicantJpaRepository,
) : ProjectCommandPort {
    override fun save(project: Project): Project {
        val projectEntity = ProjectJpaEntity.fromDomain(project)
        val savedProjectEntity = projectJpaRepository.save(projectEntity)
        val applicantIds = projectApplicantJpaRepository.findByProjectId(savedProjectEntity.id!!).map { it.applicant.id!! }
        return savedProjectEntity.toDomain(applicantIds)
    }
}
