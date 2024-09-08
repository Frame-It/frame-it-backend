package com.org.framelt.project.application.port.`in`

interface ProjectReadUseCase {
    fun getProjectAnnouncementDetail(projectId: Long): ProjectAnnouncementDetailModel

    fun getProjectAnnouncementList(projectFilterCommand: ProjectFilterCommand): List<ProjectAnnouncementItemModel>

    fun getRecruitingProjectForHost(projectId: Long): RecruitingProjectDetailHostModel
}
