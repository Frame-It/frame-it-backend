package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.GuestModel
import java.time.LocalDate

data class GuestResponse(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val appliedAt: LocalDate,
    val applyContent: String,
) {
    companion object {
        fun from(guestModel: GuestModel): GuestResponse =
            GuestResponse(
                id = guestModel.id,
                nickname = guestModel.nickname,
                profileImageUrl = guestModel.profileImageUrl,
                appliedAt = guestModel.appliedAt,
                applyContent = guestModel.applyContent,
            )
    }
}
