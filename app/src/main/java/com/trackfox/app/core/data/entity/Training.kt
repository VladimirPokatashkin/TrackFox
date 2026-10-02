package com.trackfox.app.core.data.entity

import java.time.LocalDate

data class Training(
    val date : LocalDate,
    val duration : Int,
    val averageHR : Int,
    val maxHR : Int
)