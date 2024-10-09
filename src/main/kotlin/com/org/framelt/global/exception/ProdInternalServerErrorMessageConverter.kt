package com.org.framelt.global.exception

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("prod")
@Component
class ProdInternalServerErrorMessageConverter : InternalServerErrorMessageConverter {
    override fun convert(throwable: Throwable): String = "서버 에러 발생"
}
