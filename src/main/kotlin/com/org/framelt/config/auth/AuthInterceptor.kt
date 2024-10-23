package com.org.framelt.config.auth

import com.org.framelt.user.adapter.out.jwt.JwtProvider
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsUtils
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthInterceptor(
    val jwtProvider: JwtProvider,
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true
        }
        // 회원가입 요청은 인증 없이 허용(HTTP 메서드에 따른 구분하기 전 임시로 설정)
        if (request.requestURI == "/users" && request.method == "POST") {
            return true
        }
        val authHeader =
            request.getHeader(HttpHeaders.AUTHORIZATION)
                ?: throw IllegalArgumentException("인증 정보가 없습니다.")

        val userId = jwtProvider.parseToken(authHeader)
        request.setAttribute("userId", userId)
        return true
    }
}
