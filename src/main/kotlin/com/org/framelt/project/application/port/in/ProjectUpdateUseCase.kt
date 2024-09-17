package com.org.framelt.project.application.port.`in`

interface ProjectUpdateUseCase {
    fun update(projectUpdateCommand: ProjectUpdateCommand): Long
}
