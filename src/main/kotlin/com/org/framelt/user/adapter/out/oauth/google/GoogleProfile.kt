package com.org.framelt.user.adapter.out.oauth.google

import com.fasterxml.jackson.annotation.JsonProperty

data class GoogleProfile(
    @JsonProperty(value = "given_name") val givenName: String,
    val picture: String,
    val id: String,
    val name: String,
)
