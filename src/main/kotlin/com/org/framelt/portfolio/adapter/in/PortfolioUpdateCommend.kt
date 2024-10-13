package com.org.framelt.portfolio.adapter.`in`

import org.springframework.web.multipart.MultipartFile

data class PortfolioUpdateCommend(
    val portfolioId: Long,
    val userId: Long,
    val addPhotos: List<MultipartFile>?,
    val deletePhotos: List<String>?,
    val title: String?,
    val description: String?,
    val hashtags: List<String>?,
    val togethers: List<Long>?,
)
