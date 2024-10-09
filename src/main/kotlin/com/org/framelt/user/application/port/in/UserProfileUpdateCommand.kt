package com.org.framelt.user.application.port.`in`

import com.org.framelt.user.domain.Concept
import org.springframework.web.multipart.MultipartFile

data class UserProfileUpdateCommand(
    val userId: Long,
    val updateUserId: Long,
    val profileImage: MultipartFile?,
    val nickname: String,
    val description: String,
    val concepts: List<Concept>,
)
