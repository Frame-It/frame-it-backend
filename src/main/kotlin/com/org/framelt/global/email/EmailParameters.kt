package com.org.framelt.global.email

import com.org.framelt.notification.application.service.NotificationLetter
import com.org.framelt.notification.domain.NotificationEventType
import com.org.framelt.project.domain.Project
import com.org.framelt.user.domain.User
import java.time.LocalDate

sealed class EmailParameters {
    companion object {
        fun from(notificationLetter: NotificationLetter): EmailParameters {
            when (notificationLetter.eventType) {
                NotificationEventType.SIGN_UP -> return SignUpWelcomeEmailParameters.from(notificationLetter.receiver)
                NotificationEventType.PROJECT_APPLICATION -> return ProjectApplicationEmailParameters.from(notificationLetter.receiver, notificationLetter.project!!)
                NotificationEventType.PROJECT_START -> {
                    val applicant = if (notificationLetter.isHost!!) notificationLetter.sender else notificationLetter.receiver
                    return ProjectConfirmationEmailParameters.from(applicant, notificationLetter.project!!)
                }
                NotificationEventType.PROJECT_SHOOTING_DAY -> {
                    val applicant = if (notificationLetter.isHost!!) notificationLetter.sender else notificationLetter.receiver
                    return ProjectShootingDayEmailParameters.from(applicant, notificationLetter.project!!)
                }
                NotificationEventType.PROJECT_REVIEW_CREATED_FOR_HOST -> {
                    val applicant = if (notificationLetter.isHost!!) notificationLetter.sender else notificationLetter.receiver
                    return ProjectReviewCreatedEmailParameters.from(applicant, notificationLetter.project!!)
                }
                NotificationEventType.PROJECT_REVIEW_CREATED_FOR_APPLICANT -> {
                    val applicant = if (notificationLetter.isHost!!) notificationLetter.sender else notificationLetter.receiver
                    return ProjectReviewCreatedEmailParameters.from(applicant, notificationLetter.project!!)
                }

                else -> throw IllegalArgumentException("이벤트 타입에 해당하는 이메일 폼이 존재하지 않습니다.. (eventType: ${notificationLetter.eventType})")
            }
        }
    }
}

data class SignUpWelcomeEmailParameters(
    val nickname: String,
) : EmailParameters() {
    companion object {
        fun from(user: User): SignUpWelcomeEmailParameters {
            return SignUpWelcomeEmailParameters(user.nickname)
        }
    }
}

data class ProjectApplicationEmailParameters(
    val hostNickname: String,
    val projectTitle: String,
) : EmailParameters() {
    companion object {
        fun from(host: User, project: Project): ProjectApplicationEmailParameters {
            return ProjectApplicationEmailParameters(host.nickname, project.title)
        }
    }
}

data class ProjectConfirmationEmailParameters(
    val projectTitle: String,
    val projectShootingAt: LocalDate,
    val projectAddress: String,
    val hostNickname: String,
    val applicantNickname: String,
) : EmailParameters() {
    companion object {
        fun from(applicant: User, project: Project): ProjectConfirmationEmailParameters {
            return ProjectConfirmationEmailParameters(
                projectTitle = project.title,
                projectShootingAt = project.shootingAt.toLocalDate(),
                projectAddress = project.address,
                hostNickname = project.host.nickname,
                applicantNickname = applicant.nickname,
            )
        }
    }
}

data class ProjectShootingDayEmailParameters(
    val projectTitle: String,
    val projectShootingAt: LocalDate,
    val projectAddress: String,
    val hostNickname: String,
    val applicantNickname: String,
) : EmailParameters() {
    companion object {
        fun from(applicant: User, project: Project): ProjectShootingDayEmailParameters {
            return ProjectShootingDayEmailParameters(
                projectTitle = project.title,
                projectShootingAt = project.shootingAt.toLocalDate(),
                projectAddress = project.address,
                hostNickname = project.host.nickname,
                applicantNickname = applicant.nickname,
            )
        }
    }
}

data class ProjectReviewCreatedEmailParameters(
    val projectTitle: String,
    val hostNickname: String,
    val applicantNickname: String,
) : EmailParameters() {
    companion object {
        fun from(applicant: User, project: Project): ProjectReviewCreatedEmailParameters {
            return ProjectReviewCreatedEmailParameters(
                projectTitle = project.title,
                hostNickname = project.host.nickname,
                applicantNickname = applicant.nickname,
            )
        }
    }
}
