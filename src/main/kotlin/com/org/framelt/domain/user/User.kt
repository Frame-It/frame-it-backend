package com.org.framelt.domain.user

data class User(
    val id: Long? = null,
    val name : String,
    val nickname: String,
    val profileImageUrl: String? = null,
    val bio: String? = null,
    val identity: Identity,
    val career: String? = null,
    val shootingConcepts: List<Concept>,
    val notificationsEnabled: Boolean, //보유
    val deviseToken: String? = null
) {

}