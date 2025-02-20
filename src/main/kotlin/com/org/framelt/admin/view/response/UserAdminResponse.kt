package com.org.framelt.admin.view.response

data class UserAdminResponse(
    val id: Long,
    val name: String,
    val nickname: String,
    val email: String,
    val identity: String,
    val notificationsEnabled: Boolean,
    val inProgressProjectCount: Int,
    val completedProjectCount: Int,
    val portfolioCount: Int,
    val oauthType: String,
)
