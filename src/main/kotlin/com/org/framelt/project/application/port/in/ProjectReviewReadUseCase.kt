package com.org.framelt.project.application.port.`in`

interface ProjectReviewReadUseCase {
    fun getProjectReview(projectReviewReadCommand: ProjectReviewReadCommand): ProjectReviewModel
}
