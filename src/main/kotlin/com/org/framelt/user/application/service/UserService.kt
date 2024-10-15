package com.org.framelt.user.application.service

import com.org.framelt.portfolio.adapter.out.FileUploadClient
import com.org.framelt.project.application.port.out.ProjectMemberQueryPort
import com.org.framelt.project.domain.Status
import com.org.framelt.user.application.port.`in`.SignUpCommand
import com.org.framelt.user.application.port.`in`.SignUpUseCase
import com.org.framelt.user.application.port.`in`.UserAccountInfoModel
import com.org.framelt.user.application.port.`in`.UserAccountReadUseCase
import com.org.framelt.user.application.port.`in`.UserNicknameCheckCommand
import com.org.framelt.user.application.port.`in`.UserNicknameCheckUseCase
import com.org.framelt.user.application.port.`in`.UserProfileUpdateCommand
import com.org.framelt.user.application.port.`in`.UserProfileUseCase
import com.org.framelt.user.application.port.`in`.UserQuitCommand
import com.org.framelt.user.application.port.`in`.UserQuitUseCase
import com.org.framelt.user.application.port.out.persistence.UserCommandPort
import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import com.org.framelt.user.domain.Identity
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
) : SignUpUseCase,
    UserAccountReadUseCase,
    UserNicknameCheckUseCase,
    UserQuitUseCase,
    UserProfileUseCase {
    override fun signUp(signUpCommand: SignUpCommand) {
        val user = userQueryPort.readById(signUpCommand.id)
        require(!isNicknameDuplicated(UserNicknameCheckCommand(signUpCommand.nickname))) { "이미 존재하는 닉네임은 사용할 수 없습니다." }
        user.fillProfile(
            name = signUpCommand.name,
            birthDate = signUpCommand.birthDate,
            nickname = signUpCommand.nickname,
            notificationsEnabled = signUpCommand.notificationsEnabled,
            identity = Identity.of(signUpCommand.identity),
        )
        userCommandPort.save(user)
    }

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
            } ?: user.profileImageUrl

        val updatedUser =
            user.updateProfile(
                profileImageUrl = profileImageUrl,
                description = userProfileUpdateCommand.description,
                concepts = userProfileUpdateCommand.concepts,
            )
        userCommandPort.save(updatedUser)
    }
}
