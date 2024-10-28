package com.org.framelt.user.application.port.`in`

import com.org.framelt.user.adapter.`in`.UserDeviseTokenRequest
import com.org.framelt.user.adapter.`in`.response.UserDeviceTokenResponse

interface UserDeviseTokenUseCase {
    fun updateDeviseToken(
        userId: Long,
        deviseToken: UserDeviseTokenRequest,
    )

    fun getDeviseToken(userId: Long): UserDeviceTokenResponse
}
