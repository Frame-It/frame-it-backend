package com.org.framelt.project.domain

import com.org.framelt.user.domain.User
import java.time.LocalDateTime

class ProjectApplicant(
    val id: Long? = null,
    val project: Project,
    val applicant: User,
    val applyContent: String,
    var isCanceled: Boolean = false,
    val cancelReasons: MutableList<ProjectApplicantCancelReason> = mutableListOf(),
    var cancelContent: String? = null,
    val appliedAt: LocalDateTime = LocalDateTime.now(),
    val modifiedAt: LocalDateTime = LocalDateTime.now(),
) {
    init {
        require(applicant.id != project.host.id) { "프로젝트 호스트는 지원할 수 없습니다." }
        require(applicant.identity == project.recruitmentRole) { "모집 역할과 사용자 역할이 일치하지 않습니다." }
    }

    fun cancel(
        content: String,
        reasons: List<ProjectApplicantCancelReason>,
    ) {
        require(!isCanceled) { "이미 취소된 지원입니다." }
        isCanceled = true
        cancelReasons.addAll(reasons)
        cancelContent = content
    }

    fun accepted(projectId: Long) {
        require(!isCanceled) { "취소된 지원은 승인할 수 없습니다." }
        require(project.id == projectId) { "사용자가 지언한 프로젝트와 일치하지 않습니다." }
        require(project.status == Status.RECRUITING) { "모집 중인 프로젝트에만 승인 가능합니다." }
    }
}
