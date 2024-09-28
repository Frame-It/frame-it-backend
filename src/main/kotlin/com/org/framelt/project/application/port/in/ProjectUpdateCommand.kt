package com.org.framelt.project.application.port.`in`

import com.org.framelt.project.domain.Concept
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.TimeOption
import com.org.framelt.user.domain.LocationType
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

data class ProjectUpdateCommand(
    val userId: Long,
    val projectId: Long,
    val title: String,
    val shootingAt: LocalDateTime,
    val timeOption: TimeOption,
    val locationType: LocationType,
    val spot: Spot,
    val concepts: List<Concept>,
    val conceptPhotos: List<MultipartFile>?,
    val description: String,
    val retouchingDescription: String,
)
