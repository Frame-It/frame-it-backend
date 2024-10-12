package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.ProjectMember

data class InProgressProjectHostModel(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
) {
    companion object {
        fun fromDomain(projectMember: ProjectMember) =
            InProgressProjectHostModel(
                id = projectMember.member.id!!,
                nickname = projectMember.member.nickname,
                profileImageUrl = projectMember.member.profileImageUrl,
            )
    }
}
