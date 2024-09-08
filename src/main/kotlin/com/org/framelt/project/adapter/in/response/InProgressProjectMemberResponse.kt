package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.InProgressProjectMemberModel

data class InProgressProjectMemberResponse(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
) {
    companion object {
        fun from(inProgressProjectMemberModel: InProgressProjectMemberModel) =
            InProgressProjectMemberResponse(
                id = inProgressProjectMemberModel.id,
                nickname = inProgressProjectMemberModel.nickname,
                profileImageUrl = inProgressProjectMemberModel.profileImageUrl,
            )
    }
}
