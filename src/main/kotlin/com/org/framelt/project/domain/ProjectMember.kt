package com.org.framelt.project.domain

import com.org.framelt.user.domain.User

class ProjectMember(
    val id: Long? = null,
    val member: User,
    val project: Project,
    val isHost: Boolean = false,
    var hasCompletedProject: Boolean = false,
) {
    fun completeProject() {
        require(!hasCompletedProject) { "이미 완료한 프로젝트입니다." }
        hasCompletedProject = true
    }

    fun getUserId(): Long = member.id!!

    override fun toString(): String = "ProjectMember(id=$id, member=$member, project=$project, isHost=$isHost)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProjectMember

        return id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}
