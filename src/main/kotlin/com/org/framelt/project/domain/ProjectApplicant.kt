package com.org.framelt.project.domain

import com.org.framelt.user.domain.User

class ProjectApplicant(
    val project: Project,
    val applicant: User,
    val applyContent: String,
) {
    init {
        require(project.status == Status.RECRUITING) { "모집 중인 프로젝트에만 지원 가능합니다." }
        require(!project.applicantIds.contains(applicant.id)) { "이미 지원한 사용자입니다." }
        require(applicant.id != project.manager.id) { "프로젝트 매니저는 지원할 수 없습니다." }
        require(applicant.identity == project.recruitmentRole) { "모집 역할과 사용자 역할이 일치하지 않습니다." }
    }
}
