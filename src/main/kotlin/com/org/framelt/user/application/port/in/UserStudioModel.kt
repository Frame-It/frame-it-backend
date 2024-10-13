package com.org.framelt.user.application.port.`in`

import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.UserConcept

class UserStudioModel(
    val id: Long,
    val nickname: String,
    val identity: Identity,
    val profileImageUrl: String?,
    val portfolioCount: Int,
    val projectCount: Int,
    val description: String?,
    val concepts: List<UserConcept>,
)
