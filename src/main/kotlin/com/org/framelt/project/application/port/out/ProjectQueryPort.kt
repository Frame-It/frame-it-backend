package com.org.framelt.project.application.port.out

import com.org.framelt.project.application.port.`in`.ProjectFilterCommand
import com.org.framelt.project.domain.Project
import com.org.framelt.project.domain.Status

interface ProjectQueryPort {
    fun readById(id: Long): Project

    fun readAllByFilterAndStatus(
        projectFilterCommand: ProjectFilterCommand,
        status: Status,
    ): List<Project>

    fun readByHostIdAndStatus(
        hostId: Long,
        status: Status,
    ): List<Project>
}
