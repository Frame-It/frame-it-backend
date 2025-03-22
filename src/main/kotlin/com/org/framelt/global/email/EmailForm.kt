package com.org.framelt.global.email

import com.org.framelt.notification.domain.NotificationEventType

enum class EmailForm(
    val eventType: NotificationEventType,
    val title: String,
) {
    SIGN_UP_WELCOME(NotificationEventType.SIGN_UP,"[프레이밋] 환영합니다 :)") {
        override fun generateContent(parameters: EmailParameters): String {
            require(parameters is SignUpWelcomeEmailParameters) { "잘못된 인자가 전달됐습니다. (EmailParameters: $parameters)" }
            return """
                ${parameters.nickname} 님
                안녕하세요. ${parameters.nickname}님의 프레이밋 가입을 환영합니다.

                프레이밋은 비기너 사진 유저들을 위한 커뮤니티를 목표로 시작한 사이드프로젝트입니다.

                사이드프로젝트이지만 사진에 진심이신 유저들을 위해, 사진이란 분야에 도전하고 싶거나 이미 도전 중인 모든 유저들을 위한 플랫폼이 되기 위해
                프레이밋 팀이 최선을 다하겠습니다.

                프레이밋 가입에 감사드리며, 
                질문이나 피드백 혹은 제안이 있으시다면 언제든 아래 경로 중 하나로 연락주세요.
                주신 피드백을 바탕으로 열심히 발전해 나가겠습니다!


                - 프레이밋 X https://x.com/Frameit_kr
                - 프레이밋 이메일 frameit.2024@gmail.com
            """.trimIndent()
            }
    },
    PROJECT_APPLICATION_RECEPTION(NotificationEventType.PROJECT_APPLICATION,"[프레이밋] 프로젝트에 새로운 신청이 들어왔습니다.") {
        override fun generateContent(parameters: EmailParameters): String {
            require(parameters is ProjectApplicationEmailParameters) { "잘못된 인자가 전달됐습니다. (EmailParameters: $parameters)" }
            return """
                ${parameters.hostNickname}님의 프로젝트에 새로운 신청이 들어왔습니다.
                프로젝트를 확인해주세요!

                프로젝트: ${parameters.projectTitle}
            """.trimIndent()
        }
    },
    PROJECT_CONFIRMATION(NotificationEventType.PROJECT_START,"[프레이밋] 프로젝트가 확정되었습니다!") {
        override fun generateContent(parameters: EmailParameters): String {
            require(parameters is ProjectConfirmationEmailParameters) { "잘못된 인자가 전달됐습니다. (EmailParameters: $parameters)" }
            return """
                안녕하세요. 새로운 프로젝트가 확정되었습니다.
                -

                프로젝트: ${parameters.projectTitle}
                프로젝트 촬영일: ${parameters.projectShootingAt}
                프로젝트 장소: ${parameters.projectAddress}
                호스트: ${parameters.hostNickname}
                신청자: ${parameters.applicantNickname}
            """.trimIndent()
        }
    },
    PROJECT_SHOOTING_DAY(NotificationEventType.PROJECT_SHOOTING_DAY,"[프레이밋] 프로젝트 촬영일입니다!") {
        override fun generateContent(parameters: EmailParameters): String {
            require(parameters is ProjectShootingDayEmailParameters) { "잘못된 인자가 전달됐습니다. (EmailParameters: $parameters)" }
            return """
                안녕하세요. 오늘은 프로젝트 촬영일입니다.

                -

                프로젝트: ${parameters.projectTitle}
                프로젝트 촬영일: ${parameters.projectShootingAt}
                프로젝트 장소: ${parameters.projectAddress}
                호스트: ${parameters.hostNickname}
                신청자: ${parameters.applicantNickname}
            """.trimIndent()
        }
    },
    PROJECT_REVIEW_FOR_HOST(NotificationEventType.PROJECT_REVIEW_CREATED_FOR_HOST,"[프레이밋] 신청자가 프로젝트 리뷰를 달았습니다.") {
        override fun generateContent(parameters: EmailParameters): String {
            require(parameters is ProjectReviewCreatedEmailParameters) { "잘못된 인자가 전달됐습니다. (EmailParameters: $parameters)" }
            return """
                안녕하세요. 프로젝트 상대방이 리뷰를 달았습니다.

                -

                프로젝트: ${parameters.projectTitle}
                호스트: ${parameters.hostNickname}
                신청자: ${parameters.applicantNickname}
            """.trimIndent()
        }
    },
    PROJECT_REVIEW_FOR_APPLICANT(NotificationEventType.PROJECT_REVIEW_CREATED_FOR_APPLICANT,"[프레이밋] 호스트가 프로젝트 리뷰를 달았습니다.") {
        override fun generateContent(parameters: EmailParameters): String {
            require(parameters is ProjectReviewCreatedEmailParameters) { "잘못된 인자가 전달됐습니다. (EmailParameters: $parameters)" }
            return """
                안녕하세요. 프로젝트 상대방이 리뷰를 달았습니다.

                -

                프로젝트: ${parameters.projectTitle}
                호스트: ${parameters.hostNickname}
                신청자: ${parameters.applicantNickname}
            """.trimIndent()
        }
    },
    ;

    abstract fun generateContent(parameters: EmailParameters): String

    companion object {
        fun from(eventType: NotificationEventType) : EmailForm? = entries.find { it.eventType == eventType }
    }
}
