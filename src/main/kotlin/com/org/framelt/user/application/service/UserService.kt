package com.org.framelt.user.application.service

import com.org.framelt.portfolio.adapter.out.FileUploadClient
import com.org.framelt.project.application.port.out.ProjectMemberQueryPort
import com.org.framelt.project.domain.Status
import com.org.framelt.user.adapter.`in`.response.UserDeviceTokenResponse
import com.org.framelt.user.application.port.`in`.*
import com.org.framelt.user.application.port.out.persistence.UserCommandPort
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import jakarta.transaction.Transactional
import org.springframework.http.MediaType
import org.springframework.stereotype.Service

@Transactional
@Service
class UserService(
    val userQueryPort: UserQueryPort,
    val userCommandPort: UserCommandPort,
    val projectMemberQueryPort: ProjectMemberQueryPort,
    val fileUploadClient: FileUploadClient,
) : UserAccountReadUseCase,
    UserNicknameCheckUseCase,
    UserDeviseTokenUseCase,
    UserQuitUseCase,
    UserProfileUseCase {
    override fun getAccountInfo(userId: Long): UserAccountInfoModel {
        val user = userQueryPort.readById(userId)
        return UserAccountInfoModel(
            name = user.name,
            nickname = user.nickname,
            email = user.email,
            notificationsEnabled = user.notificationsEnabled,
        )
    }

    override fun isNicknameDuplicated(userNicknameCheckCommand: UserNicknameCheckCommand): Boolean =
        userQueryPort.existsByNickname(userNicknameCheckCommand.nickname)

    override fun hasInProgressProjects(userId: Long): Boolean {
        val user = userQueryPort.readById(userId)
        return projectMemberQueryPort.existsByMemberIdAndProjectStatus(user.id!!, Status.IN_PROGRESS)
    }

    override fun quit(userQuitCommand: UserQuitCommand) {
        val user =
            userQueryPort.findById(userQuitCommand.userId)
                ?: throw IllegalArgumentException("존재하지 않는 사용자입니다.")
        check(!projectMemberQueryPort.existsByMemberIdAndProjectStatus(user.id!!, Status.IN_PROGRESS)) { "진행 중인 프로젝트가 존재하면 탈퇴할 수 없습니다." }
        user.quit()
        userCommandPort.quit(user, userQuitCommand.quitReason)
    }

    override fun updateProfile(userProfileUpdateCommand: UserProfileUpdateCommand) {
        require(userProfileUpdateCommand.userId == userProfileUpdateCommand.updateUserId) { "프로필 수정 권한이 없습니다." }

        val user = userQueryPort.readById(userProfileUpdateCommand.userId)
        val profileImageUrl =
            userProfileUpdateCommand.profileImage?.let { it ->
                fileUploadClient
                    .upload(
                        it.originalFilename!!,
                        MediaType.IMAGE_JPEG,
                        it.bytes,
                    ).get()
            } ?: if (userProfileUpdateCommand.isDelete) null else user.profileImageUrl

        val updatedUser =
            user.updateProfile(
                profileImageUrl = profileImageUrl,
                description = userProfileUpdateCommand.description,
                concepts = userProfileUpdateCommand.concepts,
            )
        userCommandPort.save(updatedUser)
    }

    override fun updateNickname(userNicknameUpdateCommand: UserNicknameUpdateCommand) {
        require(userNicknameUpdateCommand.userId == userNicknameUpdateCommand.updateUserId) { "닉네임 수정 권한이 없습니다." }

        val nickname = userNicknameUpdateCommand.nickname
        require(!isNicknameDuplicated(UserNicknameCheckCommand(nickname))) { "이미 존재하는 닉네임은 사용할 수 없습니다." }

        val user = userQueryPort.readById(userNicknameUpdateCommand.userId)
        user.updateNickname(nickname)
        userCommandPort.save(user)
    }

    override fun updateDeviseToken(userId: Long, deviseToken: String?) {
        val user = userQueryPort.readById(userId)
        user.updateDeviseToken(deviseToken)
        userCommandPort.save(user)
    }

    override fun getDeviseToken(userId: Long): UserDeviceTokenResponse {
        val user = userQueryPort.readById(userId)
        val hasToken = !user.deviseToken.isNullOrEmpty()

        return UserDeviceTokenResponse(hasToken)
    }
}
