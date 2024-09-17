package com.org.framelt.project.application.port.out

import com.org.framelt.project.application.port.`in`.ProjectFilterCommand
import com.org.framelt.project.domain.Project

interface ProjectQueryPort {
    fun readById(id: Long): Project

    fun readAll(projectFilterCommand: ProjectFilterCommand): List<Project>
}
