package com.org.framelt.project.domain

enum class TimeOption {
    MORNING,
    AFTERNOON,
    TO_BE_DISCUSSED,
    ;

    companion object {
        fun of(timeOption: String): TimeOption = valueOf(timeOption.uppercase())
    }
}
