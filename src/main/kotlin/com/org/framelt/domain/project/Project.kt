package com.org.framelt.domain.project

import com.org.framelt.domain.user.Identity
import com.org.framelt.domain.user.LocationType
import com.org.framelt.domain.user.User
import java.time.LocalDateTime


data class Project(
    val id: Long? = null,
    val manager: User,
    val title: String,
    val identity: Identity,
    val shootingDateTime: LocalDateTime,
    val locationType: LocationType,
    val spot: Spot,
    val concept: String, //TODO: 타입 생각하기
    val photos: List<String>,
    val description: String,
    val editingDetails: String?,
    val applicants: List<User> //TODO: 객체화 시켜서 상태 관리하기

) {
    var status: Status = Status.모집중
        private set

}