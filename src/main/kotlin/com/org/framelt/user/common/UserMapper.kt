package com.org.framelt.user.common

import com.org.framelt.user.adapter.`in`.request.SignUpRequest
import com.org.framelt.user.adapter.`in`.request.UserNicknameCheckRequest
import com.org.framelt.user.adapter.`in`.request.UserNicknameUpdateRequest
import com.org.framelt.user.adapter.`in`.request.UserProfileUpdateRequest
import com.org.framelt.user.adapter.`in`.request.UserQuitRequest
import com.org.framelt.user.adapter.`in`.response.UserStudioResponse
import com.org.framelt.user.application.port.`in`.SignUpCommand
import com.org.framelt.user.application.port.`in`.UserNicknameCheckCommand
import com.org.framelt.user.application.port.`in`.UserNicknameUpdateCommand
import com.org.framelt.user.application.port.`in`.UserProfileUpdateCommand
import com.org.framelt.user.application.port.`in`.UserQuitCommand
import com.org.framelt.user.application.port.`in`.UserStudioModel
import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.UserConcept

class UserMapper {
    companion object {
        fun toCommand(signUpRequest: SignUpRequest): SignUpCommand =
            SignUpCommand(
                oauthUserId = signUpRequest.oauthUserId,
                identity = Identity.of(signUpRequest.identity),
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
                description = userProfileUpdateRequest.description,
                concepts = userProfileUpdateRequest.concepts?.map { UserConcept.fromCode(it) },
                isDelete = userProfileUpdateRequest.isDelete,
            )

        fun toCommand(
            userNicknameUpdateRequest: UserNicknameUpdateRequest,
            userId: Long,
            updateUserId: Long,
        ): UserNicknameUpdateCommand =
            UserNicknameUpdateCommand(
                userId = userId,
                updateUserId = updateUserId,
                nickname = userNicknameUpdateRequest.nickname,
            )

        fun toResponse(userStudioModel: UserStudioModel): UserStudioResponse =
            UserStudioResponse(
                id = userStudioModel.id,
                nickname = userStudioModel.nickname,
                identity = userStudioModel.identity.name,
                profileImageUrl = userStudioModel.profileImageUrl,
                portfolioCount = userStudioModel.portfolioCount,
                projectCount = userStudioModel.projectCount,
                description = userStudioModel.description,
                concepts = userStudioModel.concepts.map { it.code },
            )
    }
}
