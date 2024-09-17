package com.org.framelt.user.adapter.out.oauth.google

import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleAccessToken(
    @JsonProperty(value = "access_token") val accessToken: String,
    @JsonProperty(value = "expires_in") val expiresIn: Int,
    val scope: String,
    @JsonProperty(value = "token_type") val tokenType: String,
)
