package com.org.framelt.user.adapter.out.oauth.google

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
class GoogleOAuthClient(
    @Value("\${google.client-id}") private val clientId: String,
    @Value("\${google.client-secret}") private val clientSecret: String,
) : OAuthClient {
    override fun getProfile(
        code: String,
        redirectUri: String,
    ): OAuthProfileResponse {
        val googleAccessToken = requestGoogleAccessToken(code, redirectUri)
        val profile = requestGoogleProfile(googleAccessToken)
        return OAuthProfileResponse(
            providerUserId = profile.id,
            email = profile.email,
        )
    }

    private fun requestGoogleAccessToken(
        code: String,
        redirectUri: String,
    ): GoogleAccessToken {
        val httpHeaders =
            HttpHeaders().apply {
                contentType = MediaType.APPLICATION_FORM_URLENCODED
            }
        val googleTokenRequestBody =
            LinkedMultiValueMap<String, String>().apply {
                add("client_id", clientId)
                add("client_secret", clientSecret)
                add("code", code)
                add("grant_type", TOKEN_GRANT_TYPE)
                add("redirect_uri", redirectUri)
            }
        val httpEntity = HttpEntity(googleTokenRequestBody, httpHeaders)

        val restTemplate = RestTemplate()
        val googleAccessTokenResponse =
            restTemplate
                .exchange<GoogleAccessToken>(
                    url = ACCESS_TOKEN_REQUEST_URL,
                    method = HttpMethod.POST,
                    requestEntity = httpEntity,
                )

        if (googleAccessTokenResponse.statusCode.isError) {
            throw IllegalArgumentException("구글 토큰 요청 실패: ${googleAccessTokenResponse.body}")
        }
        return googleAccessTokenResponse.body!!
    }

    private fun requestGoogleProfile(googleAccessToken: GoogleAccessToken): GoogleProfile {
        val httpHeaders =
            HttpHeaders().apply {
                setBearerAuth(googleAccessToken.accessToken)
            }
        val httpEntity = HttpEntity(null, httpHeaders)

        val restTemplate = RestTemplate()
        val googleProfileResponse =
            restTemplate
                .exchange<GoogleProfile>(
                    url = PROFILE_REQUEST_URL,
                    method = HttpMethod.GET,
                    requestEntity = httpEntity,
                )

        if (googleProfileResponse.statusCode.isError) {
            throw IllegalArgumentException("구글 토큰 요청 실패: ${googleProfileResponse.body}")
        }
        return googleProfileResponse.body!!
    }

    override fun getOAuthProvider(): OAuthProvider = OAuthProvider.GOOGLE

    companion object {
        private const val ACCESS_TOKEN_REQUEST_URL = "https://oauth2.googleapis.com/token"
        private const val PROFILE_REQUEST_URL = "https://www.googleapis.com/userinfo/v2/me"
        private const val TOKEN_GRANT_TYPE = "authorization_code"
    }
}
