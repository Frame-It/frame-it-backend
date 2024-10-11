package com.org.framelt.user.common

import com.org.framelt.user.adapter.`in`.request.SignUpRequest
import com.org.framelt.user.adapter.`in`.request.UserNicknameCheckRequest
import com.org.framelt.user.adapter.`in`.request.UserProfileUpdateRequest
import com.org.framelt.user.adapter.`in`.request.UserQuitRequest
import com.org.framelt.user.adapter.`in`.response.UserStudioResponse
import com.org.framelt.user.application.port.`in`.SignUpCommand
import com.org.framelt.user.application.port.`in`.UserNicknameCheckCommand
import com.org.framelt.user.application.port.`in`.UserProfileUpdateCommand
import com.org.framelt.user.application.port.`in`.UserQuitCommand
import com.org.framelt.user.application.port.`in`.UserStudioModel
import com.org.framelt.user.domain.UserConcept

class UserMapper {
    companion object {
        fun toCommand(
            signUpRequest: SignUpRequest,
            userId: Long,
        ): SignUpCommand =
            SignUpCommand(
                id = userId,
                identity = signUpRequest.identity,
                name = signUpRequest.name,
                birthDate = signUpRequest.birthDate,
                nickname = signUpRequest.nickname,
                notificationsEnabled = signUpRequest.notificationsEnabled,
            )

        fun toCommand(userNicknameCheckRequest: UserNicknameCheckRequest): UserNicknameCheckCommand =
            UserNicknameCheckCommand(
                nickname = userNicknameCheckRequest.nickname,
            )

        fun toCommand(
            userId: Long,
            quitRequest: UserQuitRequest,
        ): UserQuitCommand =
            UserQuitCommand(
                userId = userId,
                quitReason = quitRequest.quitReason,
            )

        fun toCommand(
            userProfileUpdateRequest: UserProfileUpdateRequest,
            userId: Long,
            updateUserId: Long,
        ): UserProfileUpdateCommand =
            UserProfileUpdateCommand(
                userId = userId,
                updateUserId = updateUserId,
                profileImage = userProfileUpdateRequest.profileImage,
                nickname = userProfileUpdateRequest.nickname,
                description = userProfileUpdateRequest.description,
                concepts = userProfileUpdateRequest.concepts.map { UserConcept.fromCode(it) },
            )

        fun toResponse(userStudioModel: UserStudioModel): UserStudioResponse =
            UserStudioResponse(
                id = userStudioModel.id,
                nickname = userStudioModel.nickname,
                identity = userStudioModel.identity.name,
                profileImageUrl = userStudioModel.profileImageUrl,
                portfolioCount = userStudioModel.portfolioCount,
                projectCount = userStudioModel.projectCount,
            )
    }
}
