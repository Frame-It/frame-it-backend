package com.org.framelt.project.application.port.`in`

interface ProjectCompleteUseCase {
    fun complete(projectCompleteCommand: ProjectCompleteCommand): ProjectCompleteModel
}
