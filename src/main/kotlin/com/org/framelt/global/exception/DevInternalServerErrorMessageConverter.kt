package com.org.framelt.global.exception

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("!prod")
@Component
class DevInternalServerErrorMessageConverter : InternalServerErrorMessageConverter {
    override fun convert(throwable: Throwable): String = "${throwable.javaClass.simpleName}: ${throwable.localizedMessage}"
}
