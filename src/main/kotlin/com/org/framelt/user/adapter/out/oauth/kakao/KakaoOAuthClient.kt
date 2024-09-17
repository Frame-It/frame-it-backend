package com.org.framelt.user.adapter.out.oauth.kakao

import com.org.framelt.user.adapter.out.oauth.OAuthClient
import com.org.framelt.user.adapter.out.oauth.OAuthProfileResponse
import com.org.framelt.user.adapter.out.oauth.OAuthProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Component
class KakaoOAuthClient(
    @Value("\${kakao.client-id}") private val kakaoClientId: String,
    @Value("\${kakao.redirect-uri}") private val kakaoRedirectUri: String,
    @Value("\${kakao.client-secret}") private val kakaoClientSecret: String,
) : OAuthClient {
    override fun getProfile(code: String): OAuthProfileResponse {
        val kakaoAccessToken = requestKakaoAccessToken(code)
        val profile = requestKakaoProfile(kakaoAccessToken)
        return OAuthProfileResponse(
            providerUserId = profile.id.toString(),
            email = profile.kakaoAccount.email,
        )
    }

    private fun requestKakaoAccessToken(code: String): KakaoAccessToken {
        val httpHeaders =
            HttpHeaders().apply {
                contentType = MediaType.APPLICATION_FORM_URLENCODED
            }
        val kakaoTokenRequestBody =
            LinkedMultiValueMap<String, String>().apply {
                add("grant_type", TOKEN_GRANT_TYPE)
                add("client_id", kakaoClientId)
                add("redirect_uri", kakaoRedirectUri)
                add("client_secret", kakaoClientSecret)
                add("code", code)
            }
        val httpEntity = HttpEntity(kakaoTokenRequestBody, httpHeaders)

        val restTemplate = RestTemplate()
        val kakaoAccessTokenResponse =
            restTemplate
                .exchange<KakaoAccessToken>(
                    url = ACCESS_TOKEN_REQUEST_URL,
                    method = HttpMethod.POST,
                    requestEntity = httpEntity,
                )

        if (kakaoAccessTokenResponse.statusCode.isError) {
            throw IllegalArgumentException("카카오 토큰 요청 실패: ${kakaoAccessTokenResponse.body}")
        }
        return kakaoAccessTokenResponse.body!!
    }

    private fun requestKakaoProfile(kakaoAccessToken: KakaoAccessToken): KakaoProfile {
        val httpHeaders =
            HttpHeaders().apply {
                setBearerAuth(kakaoAccessToken.accessToken)
                contentType = MediaType.APPLICATION_FORM_URLENCODED
            }
        val httpEntity = HttpEntity(null, httpHeaders)

        val restTemplate = RestTemplate()
        val kakaoProfileResponse =
            restTemplate
                .exchange<KakaoProfile>(
                    url = PROFILE_REQUEST_URL,
                    method = HttpMethod.GET,
                    requestEntity = httpEntity,
                )

        if (kakaoProfileResponse.statusCode.isError) {
            throw IllegalArgumentException("카카오 프로필 요청 실패: ${kakaoProfileResponse.body}")
        }

        return kakaoProfileResponse.body!!
    }

    override fun getOAuthProvider(): OAuthProvider = OAuthProvider.KAKAO

    companion object {
        private const val TOKEN_GRANT_TYPE = "authorization_code"
        private const val ACCESS_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token"
        private const val PROFILE_REQUEST_URL = "https://kapi.kakao.com/v2/user/me"
    }
}
