package com.org.framelt.project.application.port.`in`

interface ProjectReadUseCase {
    fun getProjectDetail(projectId: Long): ProjectDetailModel
}