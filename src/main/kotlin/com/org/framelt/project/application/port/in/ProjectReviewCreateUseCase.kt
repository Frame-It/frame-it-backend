package com.org.framelt.project.application.port.`in`

interface ProjectReviewCreateUseCase {
    fun review(projectReviewCommand: ProjectReviewCommand): ProjectReviewResult
}
