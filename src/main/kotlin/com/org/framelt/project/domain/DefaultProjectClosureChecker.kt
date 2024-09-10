package com.org.framelt.project.domain

import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DefaultProjectClosureChecker : ProjectClosureChecker {
    override fun isClosed(
        status: Status,
        shootingAt: LocalDateTime,
        applicantCount: Int,
    ): Boolean = status != Status.RECRUITING || (applicantCount == 0 && shootingAt.isBefore(LocalDateTime.now()))
}
