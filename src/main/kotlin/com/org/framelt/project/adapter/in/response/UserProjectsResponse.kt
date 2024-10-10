package com.org.framelt.project.adapter.`in`.response

data class UserProjectsResponse(
    val nickname: String,
    val projects: List<UserProjectItemResponse>,
)
