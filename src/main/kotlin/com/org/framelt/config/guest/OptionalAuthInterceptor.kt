package com.org.framelt.config.guest

import com.org.framelt.user.adapter.out.jwt.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class OptionalAuthInterceptor(
    val jwtProvider: JwtProvider,
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val userId =
            request.getHeader(HttpHeaders.AUTHORIZATION)?.let { jwtProvider.parseToken(it) }
                ?: "0"

        request.setAttribute("userId", userId)
        return true
    }
}
