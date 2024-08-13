package com.org.framelt.domin.user

import java.time.LocalDate

data class CareerInfo(
    val title: String,
    val description: String?,
    val startDate: LocalDate,
    val endDate: LocalDate?
)