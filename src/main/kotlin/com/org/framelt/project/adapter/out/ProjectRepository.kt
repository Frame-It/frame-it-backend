package com.org.framelt.project.adapter.out

import com.org.framelt.project.application.port.`in`.ProjectFilterCommand
import com.org.framelt.project.application.port.out.ProjectCommandPort
import com.org.framelt.project.application.port.out.ProjectQueryPort
import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.Status
import org.springframework.stereotype.Repository

@Repository
class ProjectRepository(
    private val projectJpaRepository: ProjectJpaRepository,
    private val projectQueryDslRepository: ProjectQueryDslRepository,
) : ProjectCommandPort,
    ProjectQueryPort {
    override fun save(project: Project): Project {
        val projectEntity = ProjectJpaEntity.fromDomain(project)
        val savedProjectEntity = projectJpaRepository.save(projectEntity)
        return savedProjectEntity.toDomain()
    }

    override fun readById(id: Long): Project {
        val projectEntity = projectJpaRepository.findById(id)
        return projectEntity.toDomain()
    }

    override fun readAll(projectFilterCommand: ProjectFilterCommand): List<Project> {
        val projectEntities = projectQueryDslRepository.findAllByFilter(projectFilterCommand)
        return projectEntities.map { it.toDomain() }
    }

    override fun update(project: Project) {
        val projectEntity = ProjectJpaEntity.fromDomain(project)
        projectJpaRepository.save(projectEntity)
    }

    override fun readByHostIdAndStatus(
        hostId: Long,
        status: Status,
    ): List<Project> {
        val projectEntities = projectJpaRepository.findByHostIdAndStatus(hostId, status)
        return projectEntities.map { it.toDomain() }
    }
}
