package com.org.framelt.user.application.port.`in`

interface UserAccountReadUseCase {
    fun getAccountInfo(userId: Long): UserAccountInfoModel
}
