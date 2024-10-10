package com.org.framelt.portfolio.application.service

data class PortfolioResponse(
    val id: Long,
    val title: String,
    val userId: Long,
    val profileImageUrl : String,
    val userName: String
)
