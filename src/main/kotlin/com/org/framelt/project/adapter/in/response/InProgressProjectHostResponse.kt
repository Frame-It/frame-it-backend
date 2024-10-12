package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.InProgressProjectHostModel

data class InProgressProjectHostResponse(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
) {
    companion object {
        fun from(inProgressProjectHostModel: InProgressProjectHostModel) =
            InProgressProjectHostResponse(
                id = inProgressProjectHostModel.id,
                nickname = inProgressProjectHostModel.nickname,
                profileImageUrl = inProgressProjectHostModel.profileImageUrl,
            )
    }
}
