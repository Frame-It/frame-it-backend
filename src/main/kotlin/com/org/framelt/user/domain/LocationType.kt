package com.org.framelt.user.domain

enum class LocationType {
    INDOOR,
    OUTDOOR,
    ;

    companion object {
        fun of(locationType: String): LocationType = valueOf(locationType.uppercase())
    }
}
