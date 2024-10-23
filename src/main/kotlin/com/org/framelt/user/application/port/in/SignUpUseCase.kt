package com.org.framelt.user.application.port.`in`

interface SignUpUseCase {
    fun signUp(signUpCommand: SignUpCommand): SignUpResult
}
