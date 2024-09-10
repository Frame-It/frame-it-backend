package com.org.framelt.project.domain

import java.time.LocalDateTime

interface ProjectClosureChecker {
    fun isClosed(
        status: Status,
        shootingAt: LocalDateTime,
        applicantCount: Int,
    ): Boolean
}
