package com.org.framelt.portfolio.domain

import com.org.framelt.user.domain.Identity
import com.org.framelt.user.domain.User
import java.time.LocalDateTime

class Portfolio(
    val id: Long? = null,
    val manage: User,
    val title: String,
    val description: String? = null,
    val primaryPhoto: String,
    var photos: List<String>,
    val hashtags: List<String>? = null,
    val collaborators: List<User>,
    val createAt: LocalDateTime = LocalDateTime.now(),
) {
    constructor(
        manage: User,
        title: String,
        description: String?,
        photos: List<String>,
        hashtags: List<String>?,
        collaborators: List<User>,
    ) : this(null, manage, title, description, photos.get(0), photos, hashtags, collaborators)

    fun isOwnedByUser(userId: Long): Boolean {
        return manage.id == userId
    }

    fun getId(): Long {
        return id ?: throw RuntimeException("해당 포트폴리오는 잘못된 데이터입니다.")
    }

    fun isOwnedByIdentity(identity: Identity): Boolean {
        return manage.identity.equals(identity)
    }
    fun deletePhotos(deletePhotos: List<String>?) {
        if (!deletePhotos.isNullOrEmpty()) {
            this.photos = this.photos.filterNot { deletePhotos.contains(it) }
        }
    }

    fun addPhotos(addPhotos: List<String>?) {
        if (!addPhotos.isNullOrEmpty()) {
            this.photos = this.photos + addPhotos
        }
    }

    fun update(
        title: String?,
        description: String?,
        fileLinks: List<String>?,
        hashtags: List<String>?,
        togethers: List<User>?,
    ): Portfolio {
        addPhotos(fileLinks)
        val updatedTitle = title ?: this.title
        val updatedDescription = description ?: this.description
        val updatedHashtags = hashtags ?: this.hashtags
        val updatedTogethers = togethers ?: this.collaborators

        return Portfolio(
            id = this.id,
            manage = this.manage,
            title = updatedTitle,
            description = updatedDescription,
            primaryPhoto = photos[0],
            photos = photos,
            hashtags = updatedHashtags,
            collaborators = updatedTogethers,
            createAt = this.createAt
        )
    }
}
