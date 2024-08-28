package com.org.framelt.portfolio.application.service

data class PortfolioDetailResponse(
    val id: Long,
    val title: String,
    val userId: Long,
    val userName: String,
    val description: String? = null,
    val photosUrl: List<String>,
    val hashtags: List<String>? = null,
    val collaborators: String? = null
)
