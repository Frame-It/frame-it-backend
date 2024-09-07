package com.org.framelt.project.application.port.`in`

interface ProjectApplyUseCase {
    fun applyProject(projectApplyCommand: ProjectApplyCommand): ProjectApplyModel
}
