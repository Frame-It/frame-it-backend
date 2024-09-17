package com.org.framelt.project.application.port.out

import com.org.framelt.project.domain.Project

interface ProjectCommandPort {
    fun save(project: Project): Project

    fun update(project: Project)
}
