package com.org.framelt.user.application.port.out.persistence

interface OAuthUserCommandPort {
    fun save(oAuthUserModel: OAuthUserModel): OAuthUserModel
}
