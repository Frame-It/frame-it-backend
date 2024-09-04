package com.org.framelt.project.application.port.out

import com.org.framelt.project.domain.Project

interface ProjectQueryPort {
    fun readById(id: Long): Project
}
