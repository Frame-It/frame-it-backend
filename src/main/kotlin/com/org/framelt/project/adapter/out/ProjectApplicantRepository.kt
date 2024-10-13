package com.org.framelt.project.adapter.out

import com.org.framelt.project.application.port.out.ProjectApplicantCommandPort
import com.org.framelt.project.application.port.out.ProjectApplicantQueryPort
import com.org.framelt.project.domain.ProjectApplicant
import org.springframework.stereotype.Repository

@Repository
class ProjectApplicantRepository(
    private val projectApplicantJpaRepository: ProjectApplicantJpaRepository,
) : ProjectApplicantCommandPort,
    ProjectApplicantQueryPort {
    override fun save(projectApplicant: ProjectApplicant) {
        val projectApplicantJpaEntity = ProjectApplicantJpaEntity.fromDomain(projectApplicant)
        projectApplicantJpaRepository.save(projectApplicantJpaEntity)
    }

    override fun readByProjectId(projectId: Long): List<ProjectApplicant> {
        val projectApplicantJpaEntities = projectApplicantJpaRepository.getByProjectId(projectId)
        return projectApplicantJpaEntities.map { it.toDomain() }
    }

    override fun readByProjectIdAndApplicantId(
        projectId: Long,
        applicantId: Long,
    ): ProjectApplicant {
        val projectApplicantJpaEntity =
            projectApplicantJpaRepository.getByProjectIdAndApplicantId(projectId, applicantId)
        return projectApplicantJpaEntity.toDomain()
    }

    override fun existsByProjectIdAndApplicantId(
        projectId: Long,
        applicantId: Long,
    ): Boolean = projectApplicantJpaRepository.existsByProjectIdAndApplicantId(projectId, applicantId)

    override fun countApplicants(id: Long): Int = projectApplicantJpaRepository.countByProjectId(id)

    override fun readAllByApplicantId(applicantId: Long): List<ProjectApplicant> {
        val projectApplicantJpaEntities = projectApplicantJpaRepository.findByApplicantId(applicantId)
        return projectApplicantJpaEntities.map { it.toDomain() }
    }
}
