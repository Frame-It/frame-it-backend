package com.org.framelt.project.application.port.`in`

interface ProjectReadUseCase {
    fun getProjectAnnouncementDetail(projectAnnouncementDetailCommand: ProjectAnnouncementDetailCommand): ProjectAnnouncementDetailModel

    fun getProjectAnnouncementList(projectFilterCommand: ProjectFilterCommand): List<ProjectAnnouncementItemModel>

    fun getRecruitingProjectForHost(
        projectId: Long,
        userId: Long,
    ): RecruitingProjectDetailHostModel

    fun getRecruitingProjectForGuest(
        projectId: Long,
        userId: Long,
    ): RecruitingProjectDetailGuestModel

    fun getInProgressProjectForHost(
        projectId: Long,
        userId: Long,
    ): InProgressProjectDetailHostModel

    fun getInProgressProjectForGuest(
        projectId: Long,
        userId: Long,
    ): InProgressProjectDetailModel

    fun getCompletedProject(
        projectId: Long,
        userId: Long,
    ): CompletedProjectDetailModel
}
