package com.org.framelt.user.adapter.`in`.request

import org.springframework.web.multipart.MultipartFile

data class UserProfileUpdateRequest(
    val profileImage: MultipartFile?,
    val nickname: String,
    val description: String,
    val concepts: List<String>,
)
