package com.org.framelt.config

import com.org.framelt.config.auth.AuthInterceptor
import com.org.framelt.config.auth.MemberIdArgumentResolver
import com.org.framelt.config.guest.OptionalAuthArgumentResolver
import com.org.framelt.config.guest.OptionalAuthInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    val authInterceptor: AuthInterceptor,
    val memberIdArgumentResolver: MemberIdArgumentResolver,
    val optionalAuthInterceptor: OptionalAuthInterceptor,
    val optionalAuthArgumentResolver: OptionalAuthArgumentResolver,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(authInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/local/oauth2/code/**")
            .excludePathPatterns("/login/**", "/login.html")
            .excludePathPatterns("/users/nicknames/check")
            .excludePathPatterns("/users/{userId}/in-progress-projects/exists")
            .excludePathPatterns(
                "/portfolios",
                "/portfolios/{id}",
                "/portfolios/me",
                "/portfolios/user/{id}",
                "/portfolios/model",
                "/portfolios/photography",
            ).excludePathPatterns("/fake/login")
            .excludePathPatterns("/projects/announcement")
            .excludePathPatterns("/users/{userId}/studio")

        registry
            .addInterceptor(optionalAuthInterceptor)
            .addPathPatterns("/projects/announcement")
    }

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(memberIdArgumentResolver)
        resolvers.add(optionalAuthArgumentResolver)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600)
    }
}
