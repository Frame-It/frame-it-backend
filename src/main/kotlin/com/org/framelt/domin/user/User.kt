package com.org.framelt.domin.user

data class User(
    val id: Long? = null,
    val nickname: String,
    val profileImageUrl: String? = null,
    val bio: String? = null,
    val identity: Identity,
    val career: String? = null,
    val availableSchedules: List<Schedule>,
    val shootingConcepts: List<Concept>,
    val availableLocations: List<Location>,
    val notificationsEnabled: Boolean,
    val deviseToken: String? = null
) {

}