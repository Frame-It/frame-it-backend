package com.org.framelt.project.adapter.`in`.response

import com.org.framelt.project.application.port.`in`.BookmarkedProjectReadModel
import java.time.LocalDateTime

data class BookmarkedProjectReadResponse(
    val id: Long,
    val title: String,
    val recruitmentRole: String,
    val spot: String,
    val shootingAt: LocalDateTime,
    val timeOption: String,
    val concepts: List<String>,
) {
    companion object {
        fun from(bookmarkedProjectReadModel: BookmarkedProjectReadModel) =
            BookmarkedProjectReadResponse(
                id = bookmarkedProjectReadModel.id,
                title = bookmarkedProjectReadModel.title,
                recruitmentRole = bookmarkedProjectReadModel.recruitmentRole.name,
                spot = bookmarkedProjectReadModel.spot.name,
                shootingAt = bookmarkedProjectReadModel.shootingAt,
                timeOption = bookmarkedProjectReadModel.timeOption.name,
                concepts = bookmarkedProjectReadModel.concepts.map { it.code },
            )
    }
}
