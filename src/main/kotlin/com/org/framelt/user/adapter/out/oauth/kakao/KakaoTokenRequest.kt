package com.org.framelt.user.adapter.out.oauth.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoTokenRequest(
    @JsonProperty(value = "grant_type") val grantType: String,
    @JsonProperty(value = "client_id") val clientId: String,
    @JsonProperty(value = "redirect_uri") val redirectUri: String,
    @JsonProperty(value = "client_secret") val clientSecret: String,
    val code: String,
)
