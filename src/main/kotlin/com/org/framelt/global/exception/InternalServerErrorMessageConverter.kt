package com.org.framelt.global.exception

interface InternalServerErrorMessageConverter {
    fun convert(throwable: Throwable): String
}
