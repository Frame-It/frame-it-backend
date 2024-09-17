package com.org.framelt.user.adapter.`in`.response

import com.org.framelt.user.application.port.`in`.UserAccountInfoModel

data class UserAccountInfoResponse(
    val name: String,
    val nickname: String,
    val email: String,
    val notificationsEnabled: Boolean,
) {
    companion object {
        fun from(accountInfo: UserAccountInfoModel): UserAccountInfoResponse =
            UserAccountInfoResponse(
                name = accountInfo.name,
                nickname = accountInfo.nickname,
                email = accountInfo.email,
                notificationsEnabled = accountInfo.notificationsEnabled,
            )
    }
}
