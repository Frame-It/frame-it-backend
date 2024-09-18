package com.org.framelt.config.auth

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AuthConfig(
    val authInterceptor: AuthInterceptor,
    val memberIdArgumentResolver: MemberIdArgumentResolver,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(authInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/local/oauth2/code/**")
            .excludePathPatterns("/login", "/login.html")
            .excludePathPatterns("/users/nicknames/check")
            .excludePathPatterns("/users/{userId}/in-progress-projects/exists")
            .excludePathPatterns("/fake/login")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(memberIdArgumentResolver)
    }
}
