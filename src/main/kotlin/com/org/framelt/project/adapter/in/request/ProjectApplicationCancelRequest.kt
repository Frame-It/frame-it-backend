package com.org.framelt.project.adapter.`in`.request

data class ProjectApplicationCancelRequest(
    val cancelReasons: List<String>,
    val content: String,
)
