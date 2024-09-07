package com.org.framelt.project.domain

import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.LocationType
import com.org.framelt.user.domain.User
import java.time.LocalDateTime

data class Project(
    val id: Long? = null,
    val manager: User,
    val title: String,
    val recruitmentRole: Identity,
    val shootingAt: LocalDateTime,
    val timeOption: TimeOption,
    val locationType: LocationType,
    val spot: Spot,
    val concepts: List<Concept>,
    val conceptPhotoUrls: List<String>,
    val description: String,
    val retouchingDescription: String?,
    val applicantIds: MutableList<Long>,
) {
    var status: Status = Status.RECRUITING
        private set

    fun update(
        title: String,
        shootingAt: LocalDateTime,
        timeOption: TimeOption,
        locationType: LocationType,
        spot: Spot,
        concepts: List<Concept>,
        conceptPhotoUrls: List<String>,
        description: String,
        retouchingDescription: String?,
    ): Project =
        Project(
            id = this.id,
            manager = this.manager,
            title = title,
            recruitmentRole = this.recruitmentRole,
            shootingAt = shootingAt,
            timeOption = timeOption,
            locationType = locationType,
            spot = spot,
            concepts = concepts,
            conceptPhotoUrls = conceptPhotoUrls,
            description = description,
            retouchingDescription = retouchingDescription,
            applicantIds = this.applicantIds,
        )

    fun start() {
        require(status == Status.RECRUITING) { "모집 중인 상태의 프로젝트만 시작 가능합니다." }
        status = Status.IN_PROGRESS
    }

    fun complete() {
        require(status == Status.IN_PROGRESS) { "진행 중인 상태의 프로젝트만 완료 가능합니다." }
        status = Status.COMPLETED
    }

    fun cancel() {
        status = Status.CANCELED
    }

    fun validateApplicantAcceptance(applicant: User) {
        require(status == Status.RECRUITING) { "모집 중인 상태의 프로젝트만 신청자를 수락할 수 있습니다." }
        require(applicantIds.contains(applicant.id)) { "신청자만 수락할 수 있습니다." }
    }
}
