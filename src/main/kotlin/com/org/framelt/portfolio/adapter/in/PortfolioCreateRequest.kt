package com.org.framelt.portfolio.adapter.`in`

import org.springframework.web.multipart.MultipartFile

data class PortfolioCreateRequest(
    val images: List<MultipartFile>,
    val title: String,
    val description: String,
    val hashTags: List<String>,
    val togethers: List<Long>
)
