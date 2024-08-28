package com.org.framelt.portfolio.domain

import com.org.framelt.user.domain.User
import org.springframework.web.multipart.MultipartFile

class Portfolio(
    val id: Long? = null,
    val manage: User,
    val title: String,
    val description: String? = null,
    val primaryPhoto: MultipartFile,
    val photos: List<MultipartFile>,
    val hashtags: List<String>? = null,
    val collaborators: List<User>,
) {
    //TODO:  photo 관련 수정
    constructor(
        manage: User,
        title: String,
        description: String?,
        photos: List<MultipartFile>,
        hashtags: List<String>?,
        collaborators: List<User>,
    ) : this(null, manage, title, description, photos.get(0), photos, hashtags, collaborators)

    fun isOwnedByUser(userId: Long): Boolean {
        return manage.id == userId
    }

    fun getId(): Long {
        return id ?: throw RuntimeException("해당 포트폴리오는 잘못된 데이터입니다.")
    }
}
