package com.org.framelt.project.domain

enum class Status {
    RECRUITING,
    IN_PROGRESS,
    COMPLETED,
    CANCELED,
    ;

    companion object {
        fun fromNullable(status: String?): Status? = status?.let { valueOf(it.uppercase()) }
    }
}
