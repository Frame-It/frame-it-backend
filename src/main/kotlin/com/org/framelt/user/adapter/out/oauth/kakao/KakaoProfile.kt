package com.org.framelt.user.adapter.out.oauth.kakao

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoProfile(
    val id: Long,
    @JsonProperty(value = "kakao_account") val kakaoAccount: KakaoAccount,
)
