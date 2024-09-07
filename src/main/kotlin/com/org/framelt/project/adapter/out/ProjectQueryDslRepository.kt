package com.org.framelt.project.adapter.out

import com.org.framelt.project.adapter.out.QProjectJpaEntity.projectJpaEntity
import com.org.framelt.project.application.port.`in`.ProjectFilterCommand
import com.org.framelt.project.domain.Concept
import com.org.framelt.project.domain.Spot
import com.org.framelt.project.domain.TimeOption
import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.LocationType
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.util.StringUtils
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ProjectQueryDslRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    fun findAllByFilter(projectFilterCommand: ProjectFilterCommand): List<ProjectJpaEntity> =
        jpaQueryFactory
            .selectFrom(projectJpaEntity)
            .where(
                matchesRecruitmentRole(projectFilterCommand.recruitmentRole),
                shootingAtBetween(projectFilterCommand.startDate, projectFilterCommand.endDate),
                matchesTimeOption(projectFilterCommand.timeOption),
                matchesSpot(projectFilterCommand.spot),
                matchesLocationType(projectFilterCommand.locationType),
                conceptsIn(projectFilterCommand.concepts),
            ).fetch()

    private fun matchesRecruitmentRole(recruitmentRole: String?): BooleanExpression? {
        if (StringUtils.isNullOrEmpty(recruitmentRole)) {
            return null
        }
        return projectJpaEntity.recruitmentRole.eq(Identity.of(recruitmentRole!!))
    }

    private fun shootingAtBetween(
        startDate: LocalDate?,
        endDate: LocalDate?,
    ): BooleanExpression? {
        if (startDate == null || endDate == null) {
            return null
        }
        return projectJpaEntity.shootingAt.between(startDate.atStartOfDay(), endDate.atTime(23, 59, 59))
    }

    private fun matchesTimeOption(timeOption: String?): BooleanExpression? {
        if (StringUtils.isNullOrEmpty(timeOption)) {
            return null
        }
        return projectJpaEntity.timeOption.eq(TimeOption.of(timeOption!!))
    }

    private fun matchesSpot(spot: String?): BooleanExpression? {
        if (StringUtils.isNullOrEmpty(spot)) {
            return null
        }
        return projectJpaEntity.spot.eq(Spot.of(spot!!))
    }

    private fun matchesLocationType(locationType: String?): BooleanExpression? {
        if (StringUtils.isNullOrEmpty(locationType)) {
            return null
        }
        return projectJpaEntity.locationType.eq(LocationType.of(locationType!!))
    }

    private fun conceptsIn(concepts: List<String>?): BooleanExpression? {
        if (concepts.isNullOrEmpty()) {
            return null
        }
        return projectJpaEntity.concepts.any().`in`(concepts.map { Concept.of(it) })
    }
}
