package com.org.framelt.user.adapter.out.oauth.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoAccessToken(
    @JsonProperty(value = "token_type") val tokenType: String,
    @JsonProperty(value = "access_token") val accessToken: String,
    @JsonProperty(value = "expires_in") val expiresIn: Int,
    @JsonProperty(value = "refresh_token") val refreshToken: String,
    @JsonProperty(value = "refresh_token_expires_in") val refreshTokenExpiresIn: Int,
)
