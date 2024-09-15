package com.org.framelt.config.auth

import com.org.framelt.user.application.port.out.persistence.UserQueryPort
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class MemberIdArgumentResolver(
    val userQueryPort: UserQueryPort,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean =
        parameter.parameterType.isAssignableFrom(Long::class.java) &&
            parameter.hasParameterAnnotation(Authorization::class.java)

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any? {
        val memberId = webRequest.getAttribute("memberId", NativeWebRequest.SCOPE_REQUEST) as String
        val user = userQueryPort.findById(memberId.toLong()) ?: throw IllegalArgumentException("존재하지 않는 사용자입니다.")
        return user.id
    }
}
