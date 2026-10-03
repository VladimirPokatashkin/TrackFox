package com.trackfox.app.entity

import java.time.LocalDate

data class Training(
    val date : LocalDate,
    val duration : Int,
    val averageHR : Int,
    val maxHR : Int
)