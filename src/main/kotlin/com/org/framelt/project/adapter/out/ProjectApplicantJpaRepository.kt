package com.org.framelt.project.adapter.out

import org.springframework.data.repository.Repository

interface ProjectApplicantJpaRepository : Repository<ProjectApplicantJpaEntity, Long> {
    fun save(projectApplicantJpaEntity: ProjectApplicantJpaEntity): ProjectApplicantJpaEntity

    fun getByProjectId(projectId: Long): List<ProjectApplicantJpaEntity>

    fun findByProjectIdAndApplicantId(
        projectId: Long,
        applicantId: Long,
    ): ProjectApplicantJpaEntity?

    fun existsByProjectIdAndApplicantId(
        projectId: Long,
        applicantId: Long,
    ): Boolean

    fun countByProjectId(id: Long): Int
}

fun ProjectApplicantJpaRepository.getByProjectIdAndApplicantId(
    projectId: Long,
    applicantId: Long,
): ProjectApplicantJpaEntity =
    findByProjectIdAndApplicantId(projectId, applicantId)
        ?: throw IllegalArgumentException("해당하는 지원자가 존재하지 않습니다.")
