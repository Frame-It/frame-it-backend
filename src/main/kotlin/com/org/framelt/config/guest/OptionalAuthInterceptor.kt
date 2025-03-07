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
            request.getHeader(HttpHeaders.AUTHORIZATION)?.let {
                val claims = jwtProvider.parseToken(it)
                require(claims["type"] == "access") { "액세스 토큰을 통해서만 인증할 수 있습니다." }
                claims.subject
            } ?: "0"

        request.setAttribute("userId", userId)
        return true
    }
}
