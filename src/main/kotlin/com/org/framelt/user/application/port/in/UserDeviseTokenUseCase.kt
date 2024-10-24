package com.org.framelt.user.application.port.`in`

import com.org.framelt.user.adapter.`in`.response.UserDeviceTokenResponse

interface UserDeviseTokenUseCase {
    fun updateDeviseToken(userId: Long, deviseToken: String?)
    fun getDeviseToken(userId: Long): UserDeviceTokenResponse

}