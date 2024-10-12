package com.org.framelt.portfolio.adapter.`in`

import org.springframework.web.multipart.MultipartFile

data class PortfoliosCreateRequest(
    val photos: List<MultipartFile>,
    val title: String,
    val description: String,
    val hashtags: List<String>,
    val togethers: List<Long>,
)