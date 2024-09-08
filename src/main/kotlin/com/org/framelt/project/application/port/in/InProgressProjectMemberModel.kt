package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.ProjectMember

data class InProgressProjectMemberModel(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
) {
    companion object {
        fun fromDomain(projectMember: ProjectMember) =
            InProgressProjectMemberModel(
                id = projectMember.member.id!!,
                nickname = projectMember.member.nickname,
                profileImageUrl = projectMember.member.profileImageUrl,
            )
    }
}
