package com.org.framelt.user.adapter.`in`.response

data class UserStudioResponse(
    val id: Long,
    val nickname: String,
    val identity: String,
    val profileImageUrl: String?,
    val portfolioCount: Int,
    val projectCount: Int,
    val description: String?,
    val concepts: List<String>,
)
