package com.org.framelt.project.adapter.`in`.request

import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.TimeOption
import com.org.framelt.user.domain.LocationType
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

data class ProjectCreateRequest(
    val title: String,
    val recruitmentRole: String,
    val shootingAt: LocalDateTime,
    val timeOption: TimeOption,
    val locationType: LocationType,
    val spot: Spot,
    val concepts: List<String>,
    val conceptPhotos: List<MultipartFile>?,
    val description: String,
    val retouchingDescription: String,
)
