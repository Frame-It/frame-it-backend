package com.org.framelt.project.application.port.`in`

interface ProjectCreateUseCase {
    fun create(projectCreateCommand: ProjectCreateCommand): Long
}
