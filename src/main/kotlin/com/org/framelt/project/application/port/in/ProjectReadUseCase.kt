package com.org.framelt.project.application.port.`in`

interface ProjectReadUseCase {
    fun getProjectAnnouncementDetail(projectId: Long): ProjectAnnouncementDetailModel

    fun getProjectAnnouncementList(projectFilterCommand: ProjectFilterCommand): List<ProjectAnnouncementItemModel>

    fun getRecruitingProjectForHost(
        projectId: Long,
        userId: Long,
    ): RecruitingProjectDetailHostModel

    fun getRecruitingProjectForGuest(
        projectId: Long,
        userId: Long,
    ): RecruitingProjectDetailGuestModel

    fun getInProgressProject(
        projectId: Long,
        userId: Long,
    ): InProgressProjectDetailModel

    fun getCompletedProject(
        projectId: Long,
        userId: Long,
    ): CompletedProjectDetailModel
}
