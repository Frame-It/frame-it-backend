package com.org.framelt.project.adapter.out

import com.org.framelt.project.application.port.`in`.ProjectFilterCommand
import com.org.framelt.project.application.port.out.ProjectCommandPort
import com.org.framelt.project.application.port.out.ProjectQueryPort
import com.org.framelt.project.domain.Project
import org.springframework.stereotype.Repository

@Repository
class ProjectRepository(
    private val projectJpaRepository: ProjectJpaRepository,
    private val projectQueryDslRepository: ProjectQueryDslRepository,
    private val projectApplicantJpaRepository: ProjectApplicantJpaRepository,
) : ProjectCommandPort,
    ProjectQueryPort {
    override fun save(project: Project): Project {
        val projectEntity = ProjectJpaEntity.fromDomain(project)
        val savedProjectEntity = projectJpaRepository.save(projectEntity)
        return savedProjectEntity.toDomain(project.applicantIds)
    }

    override fun readById(id: Long): Project {
        val projectEntity = projectJpaRepository.findById(id)
        val applicantIds = projectApplicantJpaRepository.findByProjectId(projectEntity.id!!).map { it.applicant.id!! }
        return projectEntity.toDomain(applicantIds)
    }

    override fun readAll(projectFilterCommand: ProjectFilterCommand): List<Project> {
        val projectEntities = projectQueryDslRepository.findAllByFilter(projectFilterCommand)
        return projectEntities.map { it.toDomain(applicantIds = emptyList()) }
    }

    override fun update(project: Project) {
        val projectEntity = ProjectJpaEntity.fromDomain(project)
        projectJpaRepository.save(projectEntity)
    }
}
