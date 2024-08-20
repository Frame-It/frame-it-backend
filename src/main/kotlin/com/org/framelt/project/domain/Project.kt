package com.org.framelt.project.domain

import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.LocationType
import com.org.framelt.user.domain.User
import java.time.LocalDateTime

data class Project(
    val id: Long? = null,
    val manager: User,
    val title: String,
    val identity: Identity,
    val shootingDateTime: LocalDateTime,
    val locationType: LocationType,
    val spot: Spot,
    val concept: String, // TODO: 타입 생각하기
    val photos: List<String>,
    val description: String,
    val editingDetails: String?,
    val applicants: List<User>, // TODO: 객체화 시켜서 상태 관리하기
) {
    var status: Status = Status.모집중
        private set
}
