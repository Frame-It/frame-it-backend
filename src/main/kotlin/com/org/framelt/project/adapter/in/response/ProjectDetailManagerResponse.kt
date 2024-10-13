package com.org.framelt.project.adapter.`in`.response

data class ProjectDetailManagerResponse(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val description: String?,
    val identity: String,
)
